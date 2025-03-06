package com.example.retrofitapp.routes

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.retrofitapp.ui.components.BottomNavigationBar
import com.example.retrofitapp.view.*
import com.example.retrofitapp.viewmodel.WeatherViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavGraph(navController: NavHostController, weatherViewModel: WeatherViewModel) {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val startDestination = when {
        screenWidth < 600 -> "home" // ✅ La Home será la primera pantalla en dispositivos compactos
        screenWidth in 600..900 -> "medium"
        else -> "extended"
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) } // ✅ Agregar barra de navegación inferior
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            // ✅ Pantallas adaptadas a distintos tamaños de pantalla
            composable("compact") { CompactScreen(weatherViewModel) }
            composable("medium") { MediumScreen(weatherViewModel) }
            composable("extended") { ExtendedScreen(weatherViewModel) }

            // ✅ Pantallas principales de la app
            composable("home") { HomeScreen(weatherViewModel) }
            composable("search") { SearchScreen(weatherViewModel) }
            composable("settings") { SettingsScreen() } // ✅ Se agregó la pantalla de Ajustes
        }
    }
}
