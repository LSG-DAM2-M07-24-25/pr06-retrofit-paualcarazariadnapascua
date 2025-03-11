package com.example.retrofitapp.view.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrofitapp.model.WindowSizeClassSearchh
import com.example.retrofitapp.model.getWindowSizeClass

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
    val windowSizeClass = getWindowSizeClass(context)

    // 🔹 Layout principal
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D47A1), Color(0xFF42A5F5))
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        if (isLandscape || windowSizeClass == WindowSizeClassSearchh.EXPANDED) {
            // 🖥️ **Diseño horizontal o pantallas grandes**
            Row(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SettingsContent(modifier = Modifier.fillMaxWidth(0.6f))
            }
        } else {
            // 📱 **Diseño vertical (móviles y pantallas pequeñas)**
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()), // 🔹 Permite desplazamiento en pantallas pequeñas
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SettingsContent(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun SettingsContent(modifier: Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Ajustes",
            fontSize = 26.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 **Acerca de la aplicación** (Diseño mejorado)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(10.dp, shape = RoundedCornerShape(16.dp)), // 🔹 Sombra y bordes redondeados
            colors = CardDefaults.cardColors(
                containerColor = Color.White // 🔹 Fondo blanco
            ),
            shape = RoundedCornerShape(16.dp), // 🔹 Bordes redondeados
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp) // 🔹 Elevación para sombra
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFE3F2FD), Color.White) // 🔹 Degradado suave
                        )
                    )
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, contentDescription = "Info", tint = Color(0xFF0D47A1))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Acerca de la aplicación", fontSize = 20.sp, color = Color(0xFF0D47A1))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Esta aplicación muestra el clima en tiempo real usando OpenWeatherMap API.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
