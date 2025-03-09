package com.example.retrofitapp.model

import android.app.Activity
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.window.layout.WindowMetricsCalculator

// 🔹 Enumeración de tamaños de pantalla
enum class WindowSizeClassSearchh {
    COMPACT, MEDIUM, EXPANDED
}

// 🔹 Función para obtener el tamaño de pantalla
fun getWindowSizeClass(context: Context): WindowSizeClassSearchh {
    val activity = context as Activity
    val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(activity)
    val widthDp = metrics.bounds.width() / activity.resources.displayMetrics.density

    return when {
        widthDp < 600 -> WindowSizeClassSearchh.COMPACT // 📱 Móviles pequeños
        widthDp < 840 -> WindowSizeClassSearchh.MEDIUM  // 📲 Tablets pequeñas
        else -> WindowSizeClassSearchh.EXPANDED // 💻 Tablets grandes o escritorio
    }
}
