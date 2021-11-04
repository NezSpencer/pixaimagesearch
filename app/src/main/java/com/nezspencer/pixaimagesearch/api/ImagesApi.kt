package com.nezspencer.pixaimagesearch.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.nezspencer.pixaimagesearch.BuildConfig
import com.nezspencer.pixaimagesearch.data.ImageDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {
    @GET(".")
    suspend fun fetchImages(
        @Query("q") query: String,
        @Query("key") key: String = BuildConfig.API_TOKEN
    ): NetworkResponse<ImageDataResponse, String>
}