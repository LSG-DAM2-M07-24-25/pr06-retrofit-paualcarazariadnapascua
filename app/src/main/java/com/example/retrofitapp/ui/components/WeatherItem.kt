package com.example.retrofitapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retrofitapp.model.WeatherEntity

@Composable
fun WeatherItem(weather: WeatherEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Ciudad: ${weather.city}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Temperatura: ${weather.temperature}°C", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Humedad: ${weather.humidity}%", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Descripción: ${weather.description}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
