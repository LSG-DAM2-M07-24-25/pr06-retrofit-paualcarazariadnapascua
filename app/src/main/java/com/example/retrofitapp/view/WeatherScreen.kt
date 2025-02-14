package com.example.retrofitapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.retrofitapp.model.WeatherResponse
import com.example.retrofitapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val weatherData by viewModel.weatherData.observeAsState()
    val isLoading by viewModel.loading.observeAsState(false)
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.fetchWeather(city, apiKey) }) {
            Text(text = "Buscar Clima")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading == true -> CircularProgressIndicator()
            errorMessage != null -> Text(text = errorMessage!!, color = Color.Red)
            weatherData != null -> WeatherInfo(weatherData!!)
        }

    }
}

@Composable
fun WeatherInfo(weather: WeatherResponse) {
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
            Text(text = weather.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Image(
                painter = rememberImagePainter("https://openweathermap.org/img/w/${weather.weather[0].icon}.png"),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )

            Text(text = "${weather.main.temp}°C", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(text = weather.weather[0].description.capitalize(), fontSize = 18.sp)
            Text(text = "Humedad: ${weather.main.humidity}%", fontSize = 16.sp)
        }
    }
}
