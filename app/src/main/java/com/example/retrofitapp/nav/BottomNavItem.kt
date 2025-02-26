package com.example.retrofitapp.nav


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Filled.Home, "Inicio")
    object Search : BottomNavItem("search", Icons.Filled.Search, "Buscar")
    object Settings : BottomNavItem("settings", Icons.Filled.Settings, "Ajustes")
}
