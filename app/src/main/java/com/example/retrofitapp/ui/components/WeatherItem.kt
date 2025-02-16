package com.example.retrofitapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retrofitapp.model.WeatherEntity

@Composable
fun WeatherItem(weather: WeatherEntity) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Ciudad: ${weather.city}")
            Text(text = "Temperatura: ${weather.temperature}°C")
            Text(text = "Humedad: ${weather.humidity}%")
            Text(text = "Descripción: ${weather.description}")
        }
    }
}
