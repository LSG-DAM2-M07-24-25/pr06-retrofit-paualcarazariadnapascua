package com.example.retrofitapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitapp.R
import com.example.retrofitapp.model.WeatherEntity
import com.example.retrofitapp.viewmodel.WeatherViewModel

@Composable
fun MediumScreen(viewModel: WeatherViewModel = viewModel()) {
    val weatherData by viewModel.weatherData.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState()

    var city by remember { mutableStateOf("Barcelona") }
    val apiKey = "59c3b2619ffb0c8f15d38d910cbb27f9"

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 📌 Columna Izquierda: Formulario de Búsqueda
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Introduce una ciudad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.fetchWeather(city, apiKey) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A))
            ) {
                Text(text = "Buscar Clima", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // 📌 Columna Derecha: Información del Clima
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                errorMessage != null -> Text(text = errorMessage!!, color = Color.Red)
                weatherData != null -> MediumWeatherCard(weatherData!!.first())
            }
        }
    }
}

@Composable
fun MediumWeatherCard(weather: WeatherEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFECEFF1))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = weather.city, fontSize = 26.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "${weather.temperature}°C", fontSize = 24.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Humedad: ${weather.humidity}%", fontSize = 18.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))


        }
    }
}
