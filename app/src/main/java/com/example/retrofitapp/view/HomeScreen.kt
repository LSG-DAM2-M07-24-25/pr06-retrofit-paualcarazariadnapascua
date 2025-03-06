package com.example.retrofitapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitapp.model.WeatherEntity
import com.example.retrofitapp.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: WeatherViewModel = viewModel()) {
    val cities = listOf(
        "London", "New York", "Tokyo", "Madrid", "Paris",
        "Berlin", "Rome", "Los Angeles", "Sydney", "Moscow",
        "Toronto", "São Paulo", "Mexico City", "Seoul", "Dubai",
        "Beijing", "Hong Kong", "Bangkok", "Istanbul", "Singapore"
    )

    val weatherList = remember { mutableStateListOf<WeatherEntity>() }
    val isLoading = remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    // Cridar la API per cada ciutat
    LaunchedEffect(Unit) {
        cities.forEach { city ->
            coroutineScope.launch {
                val weather = viewModel.fetchWeatherDirect(city)
                if (weather != null) {
                    weatherList.add(weather)
                }
            }
        }
        isLoading.value = false
    }

    // Fons amb gradient blau
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D47A1), Color(0xFF42A5F5))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ✅ TÍTOL AMB ICONA 🏙️
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationCity,
                    contentDescription = "Ciudades",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ciudades Populares",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // ✅ ESTAT DE CÀRREGA
            if (isLoading.value) {
                CircularProgressIndicator(color = Color.White)
            } else {
                // ✅ MOSTRAR LES CIUTATS EN UNA LazyColumn
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(weatherList) { weather ->
                        HomeWeatherCard(weather)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeWeatherCard(weather: WeatherEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(10.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = weather.city, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1565C0))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${weather.temperature}°C", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Humedad: ${weather.humidity}%", fontSize = 14.sp, color = Color.Gray)
        }
    }
}
