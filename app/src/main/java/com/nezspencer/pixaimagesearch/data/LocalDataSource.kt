package com.nezspencer.pixaimagesearch.data

import com.nezspencer.pixaimagesearch.database.ImageDao
import com.nezspencer.pixaimagesearch.database.ImageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface LocalDataSource {

    suspend fun saveImages(images: List<ImageData>)

    suspend fun getAllImages(): DataResponse<List<ImageData>>

    suspend fun getRelatedImages(query: String): DataResponse<List<ImageData>>

    suspend fun deleteAllImages()
}

@Singleton
class LocalDataSourceImpl @Inject constructor(private val imageDao: ImageDao): LocalDataSource {
    override suspend fun saveImages(images: List<ImageData>) = withContext(Dispatchers.IO){
        imageDao.insertAll(images)
    }

    override suspend fun getAllImages(): DataResponse<List<ImageData>> = withContext(Dispatchers.IO){
        val images = imageDao.fetchAllImages()
        DataResponse(images, NetworkState.SUCCESS)
    }

    override suspend fun getRelatedImages(query: String): DataResponse<List<ImageData>> = withContext(Dispatchers.IO){
        val images = imageDao.fetchRelatedImages(query)
        DataResponse(images, NetworkState.SUCCESS)
    }

    override suspend fun deleteAllImages() = withContext(Dispatchers.IO){
        imageDao.deleteAll()
    }
}