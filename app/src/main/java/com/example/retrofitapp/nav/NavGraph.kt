package com.example.retrofitapp.routes

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.retrofitapp.nav.Routes
import com.example.retrofitapp.view.CompactScreen
import com.example.retrofitapp.view.MediumScreen
import com.example.retrofitapp.view.ExtendedScreen
import com.example.retrofitapp.view.WeatherScreen
import com.example.retrofitapp.viewmodel.WeatherViewModel
import com.example.retrofitapp.ui.components.BottomNavigationBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavGraph(navController: NavHostController, weatherViewModel: WeatherViewModel) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val startDestination = when {
        screenWidth < 600 -> Routes.CompactScreen.route
        screenWidth in 600..900 -> Routes.MediumScreen.route
        else -> Routes.ExtendedScreen.route
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) } // ✅ Se asegura de que la barra de navegación se muestre
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(Routes.CompactScreen.route) { CompactScreen(weatherViewModel) }
            composable(Routes.MediumScreen.route) { MediumScreen(weatherViewModel) }
            composable(Routes.ExtendedScreen.route) { ExtendedScreen(weatherViewModel) }
            composable(Routes.WeatherScreen.route) { WeatherScreen(viewModel = weatherViewModel) }
        }
    }
}
