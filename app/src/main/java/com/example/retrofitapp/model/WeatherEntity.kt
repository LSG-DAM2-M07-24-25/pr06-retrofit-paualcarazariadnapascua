package com.example.retrofitapp.model

import WeatherResponse
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val city: String,       // ✅ Agregar este campo
    val temperature: Double,
    val humidity: Int,      // ✅ Agregar humedad
    val description: String

)

fun WeatherResponse.toEntity(): WeatherEntity {
    return WeatherEntity(
        city = this.name,
        temperature = this.main.temp,
        humidity = this.main.humidity, // ✅ Agregar esta línea
        description = this.weather.firstOrNull()?.description ?: "No disponible"
    )
}



