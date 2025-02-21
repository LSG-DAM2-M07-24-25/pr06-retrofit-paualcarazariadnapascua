package com.example.retrofitapp.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.retrofitapp.nav.Routes
import com.example.retrofitapp.view.CompactScreen
import com.example.retrofitapp.view.MediumScreen
import com.example.retrofitapp.view.ExtendedScreen
import com.example.retrofitapp.viewmodel.WeatherViewModel

@Composable
fun NavGraph(navController: NavHostController, weatherViewModel: WeatherViewModel) {
    NavHost(navController = navController, startDestination = Routes.CompactScreen.route) {
        composable(Routes.CompactScreen.route) { CompactScreen() }
        composable(Routes.MediumScreen.route) { MediumScreen() }
        composable(Routes.ExtendedScreen.route) { ExtendedScreen() }
    }
}
