package com.nezspencer.pixaimagesearch.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageData: ImageData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageData>)

    @Query("SELECT * FROM ImageData")
    suspend fun fetchAllImages(): List<ImageData>

    @Query("SELECT * FROM ImageData WHERE tags LIKE '%' || :query || '%' OR associatedQueries LIKE '%' || :query || '%'")
    suspend fun fetchRelatedImages(query: String): List<ImageData>

    @Query("DELETE FROM ImageData")
    suspend fun deleteAll()
}