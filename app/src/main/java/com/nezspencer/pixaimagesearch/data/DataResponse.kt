package com.nezspencer.pixaimagesearch.data

import com.nezspencer.pixaimagesearch.database.ImageData

data class DataResponse<T>(
    val data: T? = null,
    val state: NetworkState,
    val error: String? = null
)

enum class NetworkState {
    SUCCESS,
    FAILURE
}
sealed class ScreenState {
    data class SuccessState(val successData: List<ImageData>?) : ScreenState()
    object Loading : ScreenState()
    data class ErrorState(val message: String) : ScreenState()
}