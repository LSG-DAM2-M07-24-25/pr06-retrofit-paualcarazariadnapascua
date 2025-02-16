package com.example.retrofitapp.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.retrofitapp.nav.Routes


@Composable
fun MainView(navController: NavHostController = rememberNavController()) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    when {
        screenWidth < 600 -> navController.navigate(Routes.CompactScreen.route)
        screenWidth in 600..900 -> navController.navigate(Routes.MediumScreen.route)
        else -> navController.navigate(Routes.ExtendedScreen.route)
    }
}
