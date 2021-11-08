package com.nezspencer.pixaimagesearch

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.nezspencer.pixaimagesearch.data.ScreenState

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ImageDashboard(viewModel: DashboardViewModel, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        val state = viewModel.imageResultLiveData.observeAsState(ScreenState.Loading).value
        val dialogState = remember { mutableStateOf(viewModel.isDialogOpen) }
        val textState = remember { mutableStateOf(TextFieldValue()) }
        when (state) {
            is ScreenState.Loading -> {
                viewModel.showErrorDialog = false
                viewModel.showLoader = true
            }
            is ScreenState.SuccessState -> {
                viewModel.showErrorDialog = false
                viewModel.showLoader = false

            }
            is ScreenState.ErrorState -> {
                viewModel.showLoader = false
                viewModel.showErrorDialog = true
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.view_margin_large)))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.view_margin_large)), verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = textState.value,
                    placeholder = { Text(text = "Enter keyword to search")},
                    onValueChange = { textState.value = it },
                    modifier = Modifier.weight(1f, fill = true)
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.view_margin_normal)))
                Button(
                    onClick = { viewModel.findRelatedImages(textState.value.text) },
                    modifier = Modifier.wrapContentWidth(),
                    enabled = textState.value.text.isNotBlank()
                ) {
                    Text(text = stringResource(id = R.string.prompt_search))
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.view_margin_normal)))
            if (state is ScreenState.SuccessState && !state.successData.isNullOrEmpty()) {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2),
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.view_margin_normal))
                ) {
                    items(state.successData) { image ->
                        DashboardImageItem(
                            item = image,
                            onClick = { selectedImage ->
                                viewModel.selectedImage = selectedImage
                                dialogState.value = true
                            }
                        )
                    }
                }
            }
        }

        if (viewModel.showErrorDialog) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Dialog(onDismissRequest = {  }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()
                    ) {
                        Text(text = (state as ScreenState.ErrorState).message)
                        Button(onClick = { viewModel.showErrorDialog = false }) {
                            Text(text = "Dismiss")
                        }
                    }
                }
            }
        }

        if (viewModel.showLoader) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        if (dialogState.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                AlertDialog(
                    onDismissRequest = { dialogState.value = false },
                    confirmButton = {
                        Button(onClick = {
                            dialogState.value = false
                            navController.navigate(Destination.IMAGE_DETAIL)
                        }) {
                            Text(text = stringResource(R.string.action_yes))
                        }
                    }, title = {
                        Text(text = stringResource(R.string.title_confirm_dialog))
                    },
                    text = {
                        Text(text = stringResource(R.string.prompt_confirm_dialog_message))
                    },
                    dismissButton = {
                        Button(onClick = { dialogState.value = false }) {
                            Text(text = stringResource(R.string.action_cancel))
                        }
                    }
                )
            }
        }
    }
}