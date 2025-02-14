package com.example.retrofitapp.routes

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object CompactScreen : Routes("compact_screen")
    object MediumScreen : Routes("medium_screen")
    object ExtendedScreen : Routes("extended_screen")
}
