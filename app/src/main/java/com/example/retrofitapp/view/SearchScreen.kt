package com.example.retrofitapp.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitapp.model.WindowSizeClassSearchh
import com.example.retrofitapp.model.getWindowSizeClass
import com.example.retrofitapp.model.WeatherEntity
import com.example.retrofitapp.viewmodel.WeatherViewModel

@Composable
fun SearchScreen(viewModel: WeatherViewModel = viewModel()) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val windowSizeClass = getWindowSizeClass(context)

    val weatherData: List<WeatherEntity> by viewModel.weatherData.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState()

    var city by remember { mutableStateOf("Barcelona") }

    LaunchedEffect(Unit) {
        viewModel.getSavedWeather("Madrid") // 🔹 Carga el clima de una ciudad guardada
    }

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
        if (isLandscape || windowSizeClass == WindowSizeClassSearchh.EXPANDED) {
            // 🖥️ **Diseño horizontal o pantallas grandes**
            Row(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SearchContent(city, { city = it }) { viewModel.fetchWeather(city) }
                }
                Spacer(modifier = Modifier.width(20.dp))
                WeatherResult(isLoading, errorMessage, weatherData)
            }
        } else {
            // 📱 **Diseño vertical (móviles) con Scroll**
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()), // 🔹 Habilita scroll
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SearchContent(city, { city = it }) { viewModel.fetchWeather(city) }
                Spacer(modifier = Modifier.height(16.dp))
                WeatherResult(isLoading, errorMessage, weatherData)
            }
        }
    }
}

@Composable
fun WeatherResult(isLoading: Boolean, errorMessage: String?, weatherData: List<WeatherEntity>) {
    when {
        isLoading -> CircularProgressIndicator(color = Color.White)
        errorMessage != null -> Text(
            text = errorMessage,
            color = Color.Red,
            fontSize = 16.sp
        )
        weatherData.isNotEmpty() -> SearchWeatherCard(weatherData.first())
    }
}

// 🔹 **Validación y búsqueda**
@Composable
fun SearchContent(city: String, onCityChange: (String) -> Unit, onSearch: () -> Unit) {
    var isError by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = city,
            onValueChange = {
                onCityChange(it)
                isError = it.isBlank()
            },
            label = { Text("Introduce una ciudad", color = Color.Black) },
            isError = isError,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color(0xFFE3F2FD),
                cursorColor = Color.Black,
                focusedIndicatorColor = Color(0xFF1E88E5),
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .clip(MaterialTheme.shapes.medium)
        )
        if (isError) {
            Text("El campo no puede estar vacío", color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (city.isNotBlank()) {
                    onSearch()
                } else {
                    isError = true
                }
            },
            enabled = city.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
        ) {
            Text(text = "Buscar Clima", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SearchWeatherCard(weather: WeatherEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(16.dp),
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
                color = Color(0xFF1565C0)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "${weather.temperature}°C",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Humedad: ${weather.humidity}%",
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
    }
}
