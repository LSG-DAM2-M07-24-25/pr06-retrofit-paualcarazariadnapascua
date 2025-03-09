package com.example.retrofitapp.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.retrofitapp.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import com.example.retrofitapp.model.WeatherEntity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitapp.model.WindowSizeClassSearchh
import com.example.retrofitapp.model.getWindowSizeClass

@Composable
fun DetailScreen(city: String, navController: NavController, viewModel: WeatherViewModel = viewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var weather by remember { mutableStateOf<WeatherEntity?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // 🔹 Detecta la orientación y tamaño de pantalla
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val windowSizeClass = getWindowSizeClass(context)

    // 🔹 Cargar datos de la API solo si `city` no está vacío
    LaunchedEffect(city) {
        if (city.isNotBlank()) {
            coroutineScope.launch {
                weather = viewModel.fetchWeatherDirect(city)
                isLoading = false
            }
        } else {
            isLoading = false
        }
    }

    // 🔹 Diseño adaptable usando `BoxWithConstraints`
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors = listOf(Color(0xFF0D47A1), Color(0xFF42A5F5)))
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val maxWidthDp = maxWidth // ✅ Corrección para evitar error con `.dp`

        if (isLandscape || windowSizeClass == WindowSizeClassSearchh.EXPANDED) {
            // 🖥️ **Modo Horizontal o Pantallas Grandes** → `Row`
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                weather?.let { data ->
                    WeatherInfoCardDetail(data, maxWidthDp * 0.5f) // ✅ 50% del ancho
                } ?: CircularProgressIndicator(color = Color.White)

                // 🔹 **Botón de regreso siempre visible en horizontal**
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(0.3f) // ✅ Botón ocupa 30% del ancho
                ) {
                    Text(text = "Volver", color = Color(0xFF0D47A1), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            // 📱 **Modo Vertical** → Tarjeta arriba y botón debajo
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Detalles de $city",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(20.dp))

                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    weather?.let { data ->
                        WeatherInfoCardDetail(data, maxWidthDp * 0.85f) // ✅ 85% del ancho
                    } ?: Text("No se encontraron datos", color = Color.White, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 🔹 **Botón de regreso en vertical** (tamaño responsivo)
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth(0.85f) // ✅ Ocupa 85% del ancho en vertical
                        .padding(8.dp)
                ) {
                    Text(text = "Volver", color = Color(0xFF0D47A1), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ✅ 📌 **Composable para la tarjeta de información del clima**
@Composable
fun WeatherInfoCardDetail(data: WeatherEntity, width: Dp) {
    Card(
        modifier = Modifier
            .width(width) // ✅ Tamaño flexible corregido
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Ciudad: ${data.city}", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1565C0))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Temperatura: ${data.temperature}°C", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D47A1))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Humedad: ${data.humidity}%", fontSize = 18.sp, color = Color.Gray)
        }
    }
}
