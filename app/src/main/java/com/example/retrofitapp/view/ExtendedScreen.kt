package com.example.retrofitapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitapp.model.WeatherEntity

import com.example.retrofitapp.viewmodel.WeatherViewModel

@Composable
fun ExtendedScreen(viewModel: WeatherViewModel = viewModel()) {
    val weatherData by viewModel.weatherData.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState()

    var city by remember { mutableStateOf("Barcelona") }
    val apiKey = "59c3b2619ffb0c8f15d38d910cbb27f9"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), // Más espacio para Extended
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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

        Spacer(modifier = Modifier.height(24.dp)) // Más separación

        when {
            isLoading -> CircularProgressIndicator()
            errorMessage != null -> Text(text = errorMessage!!, color = Color.Red)
            weatherData != null -> ExtendedWeatherCard(weatherData!!.first()) // ✅ Nueva tarjeta
        }
    }
}

@Composable
fun ExtendedWeatherCard(weather: WeatherEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f) // Más ancho en Extended
            .padding(16.dp)
            .height(180.dp), // Tarjeta más grande
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = weather.city, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "${weather.temperature}°C", fontSize = 26.sp, color = Color.Blue)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Humedad: ${weather.humidity}%", fontSize = 18.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Condiciones: ${weather.description}", fontSize = 16.sp, color = Color.DarkGray)
        }
    }
}
