package com.nezspencer.pixaimagesearch.data

import com.nezspencer.pixaimagesearch.database.ImageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FakeLocalDataSource : LocalDataSource {
    private val localCache = mutableListOf<ImageData>()
    private var fetchRelatedImagesInteractionCount = 0
    private var saveImageInteractionCount = 0
    private var fetchAllImagesInteractionCount = 0

    override suspend fun saveImages(images: List<ImageData>) = withContext(Dispatchers.IO) {
        saveImageInteractionCount++
        for (image in images) {
            val cachedDuplicate = localCache.find { it.id == image.id }
            if (cachedDuplicate != null) {
                localCache.remove(cachedDuplicate)
            }
            localCache.add(
                ImageData(
                    image.id,
                    image.tags,
                    image.previewURL,
                    image.userImageURL,
                    image.user,
                    image.userId,
                    image.comments,
                    image.likes,
                    image.downloads,
                    image.imageUrl,
                    image.associatedQueries
                )
            )
        }
    }

    override suspend fun getAllImages(): DataResponse<List<ImageData>> =
        withContext(Dispatchers.IO) {
            fetchAllImagesInteractionCount++
            DataResponse(localCache, NetworkState.SUCCESS)
        }

    override suspend fun getRelatedImages(query: String): DataResponse<List<ImageData>> =
        withContext(Dispatchers.IO) {
            fetchRelatedImagesInteractionCount++
            DataResponse(localCache.filter {
                it.tags.contains(query) || it.associatedQueries.contains(
                    query
                )
            }, NetworkState.SUCCESS)
        }

    override suspend fun deleteAllImages() = withContext(Dispatchers.IO) {
        localCache.clear()
    }

    fun reset() {
        fetchRelatedImagesInteractionCount = 0
        saveImageInteractionCount = 0
        fetchAllImagesInteractionCount = 0
        localCache.clear()
    }

    fun getFetchRelatedImagesInteractionCount() = fetchRelatedImagesInteractionCount

    fun getSaveImageInteractionCount() = fetchRelatedImagesInteractionCount

    fun getFetchAllImagesInteractionCount() = fetchAllImagesInteractionCount
}