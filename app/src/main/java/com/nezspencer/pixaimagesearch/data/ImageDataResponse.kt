package com.nezspencer.pixaimagesearch.data

import android.os.Parcelable
import com.nezspencer.pixaimagesearch.database.ImageData
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageDataResponse(
    @Json(name = "hits")
    val images: List<ImageData>
): Parcelable