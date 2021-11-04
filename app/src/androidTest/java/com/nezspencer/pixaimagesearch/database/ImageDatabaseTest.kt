package com.nezspencer.pixaimagesearch.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ImageDatabaseTest {
    private lateinit var imageDao: ImageDao
    private lateinit var db: ImageDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ImageDatabase::class.java
        ).build()
        imageDao = db.imageDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun givenImagesToSave_insertAllSavesAllImagesToDb() = runBlocking {
        val images = listOf(
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
        imageDao.insertAll(images)
        val cachedResult = imageDao.fetchAllImages()
        var isSame = false
        images.forEachIndexed { i, image ->
            isSame = image.id == cachedResult[i].id
        }
        assertTrue(images.size == cachedResult.size)
        assertTrue(isSame)
    }

    @Test
    @Throws(IOException::class)
    fun givenDBWithSavedImagesAndAQueryString_FetchRelatedImagesReturnImageIfQueryStringExistsInTags() =
        runBlocking {
            val images = listOf(
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
            imageDao.insertAll(images)
            val relatedImages = imageDao.fetchRelatedImages("flower")
            assertEquals(1, relatedImages.size)
            assertTrue(relatedImages[0].id == images[1].id)
        }

    @Test
    @Throws(IOException::class)
    fun givenDBWithSavedImagesAndAQueryString_FetchRelatedImagesReturnImageIfQueryStringExistsInAssociatedQueries() =
        runBlocking {
            val images = listOf(
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
                    associatedQueries = "banana"
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
            imageDao.insertAll(images)
            val relatedImages = imageDao.fetchRelatedImages("banana")
            assertEquals(1, relatedImages.size)
            assertTrue(relatedImages[0].id == images[0].id)
        }

    @Test
    @Throws(IOException::class)
    fun givenDBWithSavedImagesAndANewQueryString_deleteImagesClearsAllImagesFromDb() = runBlocking{
        imageDao.deleteAll()
        val relatedImages = imageDao.fetchAllImages()
        assertEquals(0, relatedImages.size)
    }
}