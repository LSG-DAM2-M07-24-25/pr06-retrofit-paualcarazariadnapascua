package com.example.retrofitapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitapp.viewmodel.WeatherViewModel
import com.example.retrofitapp.ui.components.WeatherItem
import com.example.retrofitapp.viewmodel.WeatherViewModelFactory

@Composable

    fun CompactScreen(viewModel: WeatherViewModel){
    val weatherData by viewModel.weatherData.observeAsState(emptyList()) // ✅ Asegurar `emptyList()`
    val isLoading by viewModel.isLoading.observeAsState(false) // ✅ Asegurar `false` por defecto
    val errorMessage by viewModel.errorMessage.observeAsState()

    var city by remember { mutableStateOf("Barcelona") }
    val apiKey = "TU_API_KEY_AQUI"

    LaunchedEffect(city) {
        viewModel.fetchWeather(city, apiKey)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Introduce una ciudad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { viewModel.fetchWeather(city, apiKey) }) {
            Text(text = "Buscar Clima")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> CircularProgressIndicator()
            errorMessage != null -> Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
            weatherData.isNotEmpty() -> LazyColumn { // ✅ Verificar que no esté vacío
                items(weatherData) { weather ->
                    WeatherItem(weather = weather)
                }
            }
        }
    }
}
