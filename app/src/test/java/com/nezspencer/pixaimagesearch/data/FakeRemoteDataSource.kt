package com.nezspencer.pixaimagesearch.data

import com.nezspencer.pixaimagesearch.database.ImageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class FakeRemoteDataSource : RemoteDataSource {
    private val images = listOf(
        ImageData(
            id = 20,
            tags = "fresh fruits, bowls, fruit bowls",
            previewURL = "https://cdn.pixabay.com/photo/2017/05/11/19/44/fresh-fruits-2305192_150.jpg",
            userImageURL = "https://cdn.pixabay.com/user/2021/05/07/16-52-19-814_250x250.jpg",
            user = "silviarita",
            userId = 3142410,
            comments = 189,
            likes = 1015,
            downloads = 177112,
            imageUrl = "https://pixabay.com/get/g885d30a4de421fc3eba1c5d48cc7a6d63e888fbdaed6a6711f7cff4de8c31b8b71b63f1ffe115a63685033fa9268495b_640.jpg",
        ),
        ImageData(
            id = 1973875,
            tags = "flower, twig, corolla",
            previewURL = "https://cdn.pixabay.com/photo/2017/01/12/06/26/flowers-1973875_150.png",
            userImageURL = "https://cdn.pixabay.com/user/2017/01/12/06-23-29-350_250x250.png",
            user = "OHMAOH",
            userId = 4244193,
            comments = 251,
            likes = 1219,
            downloads = 186537,
            imageUrl = "https://pixabay.com/get/g885d30a4de421fc3eba1c5d48cc7a6d63e888fbdaed6a6711f7cff4de8c31b8b71b63f1ffe115a63685033fa9268495b_640.jpg",
        )
    )
    private var interactionCount = 0
    var isSuccess = true
    override suspend fun loadImages(query: String): DataResponse<List<ImageData>> =
        withContext(Dispatchers.IO) {
            interactionCount++
            if (isSuccess) DataResponse(images.filter { it.tags.contains(query) }, NetworkState.SUCCESS)
            else DataResponse(null, NetworkState.FAILURE, "Something went wrong")
        }

    fun resetCount() {
        interactionCount = 0
    }

    fun getInteractionCount() = interactionCount
}

