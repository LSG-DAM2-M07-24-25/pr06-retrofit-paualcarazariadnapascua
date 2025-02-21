package com.example.retrofitapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.retrofitapp.model.WeatherEntity

@Composable
fun WeatherInfo(weather: WeatherEntity) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = weather.city, fontSize = 24.sp, color = Color.Black)

            Image(
                painter = rememberImagePainter("https://openweathermap.org/img/w/${weather.description}.png"),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )

            Text(text = "${weather.temperature}°C", fontSize = 30.sp, color = Color.Black)
            Text(text = "Humedad: ${weather.humidity}%", fontSize = 16.sp, color = Color.Gray)
        }
    }
}
