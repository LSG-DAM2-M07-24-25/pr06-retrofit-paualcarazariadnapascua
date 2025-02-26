package com.example.retrofitapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.retrofitapp.model.WeatherEntity
import com.example.retrofitapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val weatherData by viewModel.weatherData.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState()

    var city by remember { mutableStateOf("Barcelona") }
    val apiKey = "59c3b2619ffb0c8f15d38d910cbb27f9"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D47A1), Color(0xFF42A5F5)) // ✅ Fondo con gradiente azul
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Introduce una ciudad", color = Color.Black) }, // ✅ Color del label cambiado a negro
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, // ✅ Fondo blanco cuando está en foco
                    unfocusedContainerColor = Color(0xFFE3F2FD), // ✅ Azul muy clarito cuando no está en foco
                    cursorColor = Color.Black, // ✅ Cursor negro
                    focusedIndicatorColor = Color(0xFF1E88E5), // ✅ Azul más oscuro para el borde
                    unfocusedIndicatorColor = Color.Gray // ✅ Borde gris cuando no está seleccionado
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.9f)) // ✅ Asegurar que el fondo no sea transparente
            )


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.fetchWeather(city, apiKey) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
            ) {
                Text(text = "Buscar Clima", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> CircularProgressIndicator(color = Color.White)
                errorMessage != null -> Text(text = errorMessage!!, color = Color.Red, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                weatherData != null -> WeatherCard(weatherData!!.first()) // ✅ Diseño mejorado
            }
        }
    }
}

@Composable
fun WeatherCard(weather: WeatherEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.85f) // ✅ Más estilizado, no tan ancho
            .padding(16.dp)
            .shadow(10.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weather.city,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "${weather.temperature}°C",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Humedad: ${weather.humidity}%",
                fontSize = 18.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE3F2FD)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🌦️", // ✅ Puedes cambiar el emoji según el clima
                    fontSize = 34.sp
                )
            }
        }
    }
}
