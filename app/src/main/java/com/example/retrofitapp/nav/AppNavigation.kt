package com.example.retrofitapp.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.retrofitapp.routes.NavGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavGraph(navController = navController) // ✅ Aquí se llama a `NavGraph`
}
