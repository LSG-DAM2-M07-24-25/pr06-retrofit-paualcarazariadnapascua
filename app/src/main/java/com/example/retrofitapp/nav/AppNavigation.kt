package com.example.retrofitapp.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.retrofitapp.routes.NavGraph
import com.example.retrofitapp.viewmodel.WeatherViewModel

@Composable
fun AppNavigation(weatherViewModel: WeatherViewModel) { // ✅ Recibe el ViewModel
    val navController = rememberNavController()
    NavGraph(navController = navController, weatherViewModel) // ✅ Lo pasa a NavGraph
}
