package com.example.retrofitapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.retrofitapp.nav.AppNavigation
import com.example.retrofitapp.repository.WeatherRepository
import com.example.retrofitapp.room.WeatherDatabase
import com.example.retrofitapp.ui.theme.RetrofitAppTheme
import com.example.retrofitapp.viewmodel.WeatherViewModel
import com.example.retrofitapp.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Inicializamos la base de datos
        val database = WeatherDatabase.getDatabase(this)
        val repository = WeatherRepository(database.weatherDao()) // ✅ Pasamos el DAO
        val weatherViewModel: WeatherViewModel by viewModels { WeatherViewModelFactory(repository) } // ✅ Pasamos el repositorio

        setContent {
            RetrofitAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(weatherViewModel) // ✅ Pasamos el ViewModel a la navegación
                }
            }
        }
    }
}
