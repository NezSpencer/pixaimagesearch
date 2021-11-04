package com.nezspencer.pixaimagesearch.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ImageRepositoryImplTest {
    private val localDataSource = FakeLocalDataSource()
    private val remoteDataSource = FakeRemoteDataSource()
    private val fakeAppCache = FakeAppCache()
    private lateinit var imageRepository: ImageRepository

    @Before
    fun setUp() {
        remoteDataSource.resetCount()
        localDataSource.reset()
        imageRepository = ImageRepositoryImpl(localDataSource, remoteDataSource, fakeAppCache)
    }

    @Test
    fun `given stale local cache, call to ImageRepository calls remoteDataSource`() = runBlocking{
        fakeAppCache.setDataValidity(false)
        imageRepository.fetchImages("fruits")
        assertTrue(remoteDataSource.getInteractionCount() == 1)
    }

    @Test
    fun `when cache is stale and imageRepository fetchImages is called, and result is not empty, saveImages is called in localDataSource`()
    = runBlocking {
        fakeAppCache.setDataValidity(false)
        imageRepository.fetchImages("fruits")
        assertTrue(remoteDataSource.getInteractionCount() == 1)
        assertTrue(localDataSource.getSaveImageInteractionCount() == 1)
    }

    @Test
    fun `given a new query and valid cache, calls to imageRepository calls remoteDataSource to fetch full result`() = runBlocking{
        fakeAppCache.setDataValidity(true)
        imageRepository.fetchImages("bowls")
        assertTrue(remoteDataSource.getInteractionCount() == 1)
    }

    @Test
    fun `given a new query and valid cache, calls to imageRepository calls localDataSource to fetch all cached images`() = runBlocking{
        fakeAppCache.setDataValidity(true)
        imageRepository.fetchImages("bowls")
        assertTrue(localDataSource.getFetchAllImagesInteractionCount() == 1)
    }

    @Test
    fun `given a new query and valid cache, related results in localDataSource are updated to with new query phrase as part of associatedQueries string`() = runBlocking{
        fakeAppCache.setDataValidity(false)
        imageRepository.fetchImages("fruits")
        val bowlRelatedImagesInDb = ArrayList(localDataSource.getRelatedImages("bowls").data!!)
        val response = (imageRepository.fetchImages("bowls") as ScreenState.SuccessState).successData!!
        var isUpdated = false
        bowlRelatedImagesInDb.forEach { localvalue ->
            val updatedResult = response.find { it.id ==localvalue.id }
            isUpdated = updatedResult != null && updatedResult.associatedQueries == localvalue.associatedQueries.plus(",bowls")
        }
        assertTrue(isUpdated)
    }

    @Test
    fun `given valid local cache, call to ImageRepository calls localDataSource`() = runBlocking{
        fakeAppCache.setDataValidity(false)
        imageRepository.fetchImages("fruits")
        imageRepository.fetchImages("fruits")
        assertTrue(localDataSource.getFetchRelatedImagesInteractionCount() == 3)
        assertTrue(remoteDataSource.getInteractionCount() == 1)
    }


}