package com.nezspencer.pixaimagesearch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.nezspencer.pixaimagesearch.database.ImageData

@Composable
fun ImageDetailScreen(image: ImageData) {
    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.view_margin_large))
            .fillMaxSize()
    ) {
        Image(
            painter = rememberImagePainter(image.imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.label_created_by))
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.view_margin_normal)))
            Text(text = image.user, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.view_margin_large)))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            items(image.tags.split(", ")) { tag ->
                Text(
                    text = String.format("#%s", tag),
                    color = colorResource(id = R.color.brand_blue)
                )
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.view_margin_large)))

        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ic_likes),
                contentDescription = stringResource(id = R.string.description_num_of_likes)
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.view_margin_large)))
            Text(text = String.format("%,d", image.likes), fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.view_margin_large)))

        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ic_comments),
                contentDescription = stringResource(id = R.string.description_num_of_comments)
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.view_margin_large)))
            Text(text = String.format("%,d", image.comments), fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.view_margin_large)))

        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ic_downloads),
                contentDescription = stringResource(id = R.string.description_num_of_dowloads)
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.view_margin_large)))
            Text(text = String.format("%,d", image.downloads), fontWeight = FontWeight.Bold)
        }
    }
}