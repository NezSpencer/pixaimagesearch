package com.nezspencer.pixaimagesearch.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.nezspencer.pixaimagesearch.api.ImagesApi
import com.nezspencer.pixaimagesearch.database.ImageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface RemoteDataSource {
    suspend fun loadImages(query: String): DataResponse<List<ImageData>>
}

@Singleton
class RemoteDataSourceImpl @Inject constructor(private val api: ImagesApi): RemoteDataSource {
    override suspend fun loadImages(query: String): DataResponse<List<ImageData>> = withContext(Dispatchers.IO){
        parseV2Response(api.fetchImages(query))
    }
}

fun parseV2Response(response: NetworkResponse<ImageDataResponse, String>): DataResponse<List<ImageData>> {
    return when (response) {
        is NetworkResponse.Success -> {
            DataResponse(
                response.body.images,
                state = NetworkState.SUCCESS
            )
        }
        is NetworkResponse.ServerError -> {
            val error = response.body
            DataResponse(
                error = error ?: "Something went wrong. Please retry",
                state = NetworkState.FAILURE
            )
        }
        is NetworkResponse.NetworkError -> {
            DataResponse(
                error = "Please check your internet connection and retry",
                state = NetworkState.FAILURE
            )
        }
        else -> {
            DataResponse(
                error = "Something went wrong. Please retry",
                state = NetworkState.FAILURE
            )
        }
    }
}