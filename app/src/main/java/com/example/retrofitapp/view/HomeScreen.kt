package com.example.retrofitapp.view

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.window.layout.WindowMetricsCalculator
import com.example.retrofitapp.model.WeatherEntity
import com.example.retrofitapp.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
@Composable
fun HomeScreen(navController: NavController, viewModel: WeatherViewModel = viewModel()) {
    val cities = listOf(
        "London", "New York", "Tokyo", "Madrid", "Paris",
        "Berlin", "Rome", "Los Angeles", "Sydney", "Moscow",
        "Toronto", "São Paulo", "Mexico City", "Seoul", "Dubai",
        "Beijing", "Hong Kong", "Bangkok", "Istanbul", "Singapore"
    )

    val weatherList = remember { mutableStateListOf<WeatherEntity>() }
    val isLoading = remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val windowSizeClass = getWindowSizeClass()

    val columns = when {
        windowSizeClass == WindowSizeClass.EXPANDED -> 3
        windowSizeClass == WindowSizeClass.MEDIUM -> 2
        isLandscape -> 2
        else -> 1
    }

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color(0xFF0D47A1), Color(0xFF42A5F5)))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationCity,
                    contentDescription = "Ciudades",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Ciudades Populares", fontSize = 26.sp, color = Color.White)
            }

            if (isLoading.value) {
                CircularProgressIndicator(color = Color.White)
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(weatherList) { weather ->
                        HomeWeatherCard(weather, navController) // 🔹 Pasa el NavController
                    }
                }
            }
        }
    }
}

@Composable
fun HomeWeatherCard(weather: WeatherEntity, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .clickable { navController.navigate("details/${weather.city}") }, // 🔹 Navegación a detalles
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = weather.city, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1565C0), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${weather.temperature}°C", fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Humedad: ${weather.humidity}%", fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
    }
}


// 🔹 Función para obtener el tamaño de pantalla con WindowSizeClass
@Composable
fun getWindowSizeClass(): WindowSizeClass {
    val context = LocalContext.current
    val activity = context as Activity

    val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
    val widthDp = metrics.bounds.width() / activity.resources.displayMetrics.density

    return when {
        widthDp < 600 -> WindowSizeClass.COMPACT // Pantallas pequeñas (móviles)
        widthDp < 840 -> WindowSizeClass.MEDIUM  // Pantallas medianas (tablets pequeñas)
        else -> WindowSizeClass.EXPANDED // Pantallas grandes (tablets grandes, escritorio)
    }
}

// 🔹 Enumeración de WindowSizeClass
enum class WindowSizeClass {
    COMPACT, MEDIUM, EXPANDED
}
