package com.example.retrofitapp.model

data class WeatherResponse(
    val name: String, // Nombre de la ciudad
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,    // Temperatura actual
    val humidity: Int    // Humedad
)

data class Weather(
    val description: String, // Descripción del clima
    val icon: String         // Icono del clima
)
