package com.example.retrofitapp.routes


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.retrofitapp.ui.components.BottomNavigationBar
import com.example.retrofitapp.view.*
import com.example.retrofitapp.view.detail.DetailScreen
import com.example.retrofitapp.view.home.HomeScreen
import com.example.retrofitapp.view.search.SearchScreen
import com.example.retrofitapp.viewmodel.WeatherViewModel
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun NavGraph(navController: NavHostController, weatherViewModel: WeatherViewModel) {
    val startDestination = "home" // ✅ Siempre inicia en "home"

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            // ✅ Definimos las pantallas correctamente
            composable("home") { HomeScreen(navController, weatherViewModel) }
            composable("search") { SearchScreen(weatherViewModel) }
            composable("details/{city}") { backStackEntry ->
                val city = backStackEntry.arguments?.getString("city") ?: ""
                DetailScreen(city, navController, weatherViewModel)
            }
            composable("settings") { SettingsScreen() }
        }
    }
}



