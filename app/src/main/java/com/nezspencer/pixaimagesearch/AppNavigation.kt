package com.nezspencer.pixaimagesearch

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Destination {
    const val HOME = "home"
    const val IMAGE_DETAIL = "detail"
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: DashboardViewModel,
    startDestination: String = Destination.HOME
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destination.HOME) {
            ImageDashboard(navController = navController, viewModel = viewModel)
        }
        
        composable(Destination.IMAGE_DETAIL) {
            ImageDetailScreen(image = viewModel.selectedImage)
        }
    }
}