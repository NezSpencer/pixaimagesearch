package com.nezspencer.pixaimagesearch

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.nezspencer.pixaimagesearch.ui.theme.PixaTheme
import com.nezspencer.pixaimagesearch.ui.theme.Purple200
import com.nezspencer.pixaimagesearch.ui.theme.Purple700

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun PixaApp(viewModel: DashboardViewModel) {
    ProvideWindowInsets{
        PixaTheme {
            Scaffold(topBar = { AppBar() }
            ) {
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AppBar() {
    Card(
        elevation = 3.dp,
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium.copy(CornerSize(0.dp)),
        backgroundColor = Purple700
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.view_margin_large)))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h5
            )
        }
    }
}