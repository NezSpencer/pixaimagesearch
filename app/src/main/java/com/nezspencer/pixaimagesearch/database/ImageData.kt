package com.nezspencer.pixaimagesearch.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class ImageData(
    @PrimaryKey
    val id: Long,
    val tags: String,
    val previewURL: String,
    val userImageURL: String,
    val user: String,
    @Json(name = "user_id")
    val userId: Long,
    val comments: Long,
    val likes: Long,
    val downloads: Long,
    @Json(name = "webformatURL")
    val imageUrl: String,
    var associatedQueries: String = ""
): Parcelable