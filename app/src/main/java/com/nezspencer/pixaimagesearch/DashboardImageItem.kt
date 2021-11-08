package com.nezspencer.pixaimagesearch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.nezspencer.pixaimagesearch.database.ImageData

@ExperimentalMaterialApi
@Composable
fun DashboardImageItem(item: ImageData, onClick: (ImageData) -> Unit) {
    Box(modifier = Modifier.padding(8.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onClick = { onClick(item) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Image(
                    painter = rememberImagePainter(item.previewURL),
                    contentDescription = null,
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.thumbnailImageHeight))
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    items(item.tags.split(", ")) { tag ->
                        Text(
                            text = String.format("#%s", tag),
                            color = colorResource(id = R.color.brand_blue)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.view_margin_normal)))
                Text(text = stringResource(id = R.string.created_by_prompt, item.user))
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.view_margin_normal)))
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewDashboardItem() {
    DashboardImageItem(item = ImageData(
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
    ), onClick = {})
}