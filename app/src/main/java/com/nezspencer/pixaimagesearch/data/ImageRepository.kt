package com.nezspencer.pixaimagesearch.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ImageRepository {
    suspend fun fetchImages(query: String): ScreenState
    suspend fun deleteImages()
}

class ImageRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appCache: AppCache
) : ImageRepository {
    override suspend fun fetchImages(query: String): ScreenState = withContext(Dispatchers.IO) {
        if (appCache.isStaleData()) {
            val result = remoteDataSource.loadImages(query)
            if (result.state == NetworkState.SUCCESS && result.data != null) {
                val images = result.data
                images.forEach {
                    if (it.associatedQueries.isBlank()) {
                        it.associatedQueries = it.associatedQueries.plus(query)
                    } else {
                        it.associatedQueries = it.associatedQueries.plus(",$query")
                    }
                }
                localDataSource.saveImages(images)
                appCache.saveTtl(System.currentTimeMillis())
            }
        } else {
            /*
            Check if this query has been searched before, it it has been, it will be recorded
            in the associatedQueries field
            * */
            val localSearchResult = localDataSource.getRelatedImages(query)
            if (localSearchResult.data == null ||
                localSearchResult.data.all{ !it.associatedQueries.contains(query) }) {
                /*
                if the database is not empty but that query string has not been entered before, fetch all the
                db results, and compare with the response gotten from the remoteDataSource.
                If an already saved imageData is returned, update the associatedQueries field in the DB
                so that searching the database again with the same query will return that imageData
                * */
                val localCacheContent = localDataSource.getAllImages()
                val networkResult = remoteDataSource.loadImages(query)
                if (networkResult.state == NetworkState.SUCCESS && networkResult.data != null) {
                    networkResult.data.forEach { networkValue ->
                        val cachedValue = localCacheContent.data!!.find{ it.id ==  networkValue.id}
                        if (cachedValue != null) {
                            networkValue.associatedQueries = cachedValue.associatedQueries.plus(",$query")
                        }

                    }
                    localDataSource.saveImages(networkResult.data)
                }
            }
        }

        // return data from database
        val dbResult = localDataSource.getRelatedImages(query)
        ScreenState.SuccessState(dbResult.data)
    }

    override suspend fun deleteImages() {
        localDataSource.deleteAllImages()
        appCache.saveTtl(0L)
    }
}