package com.example.retrofitapp.nav

sealed class Routes(val route: String) {
    object CompactScreen : Routes("compact_screen")
    object MediumScreen : Routes("medium_screen")
    object ExtendedScreen : Routes("extended_screen")
    object WeatherScreen : Routes("weather_screen") // ✅ Añadir la pantalla del clima
}
