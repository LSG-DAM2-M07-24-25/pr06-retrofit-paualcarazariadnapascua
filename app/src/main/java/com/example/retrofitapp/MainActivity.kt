package com.example.retrofitapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.retrofitapp.nav.BottomNavItem
import com.example.retrofitapp.ui.components.BottomNavigationBar
import com.example.retrofitapp.viewmodel.WeatherViewModel
import com.example.retrofitapp.viewmodel.WeatherViewModelFactory
import com.example.retrofitapp.repository.WeatherRepository
import com.example.retrofitapp.room.WeatherDatabase
import com.example.retrofitapp.api.Repository
import com.example.retrofitapp.routes.NavGraph
import com.example.retrofitapp.ui.theme.RetrofitAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = WeatherDatabase.getDatabase(this)
        val apiRepository = Repository()
        val repository = WeatherRepository(database.weatherDao(), apiRepository)

        val weatherViewModel: WeatherViewModel by viewModels { WeatherViewModelFactory(repository) }

        setContent {
            RetrofitAppTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController, weatherViewModel = weatherViewModel) // ✅ Pasamos el ViewModel correctamente
            }
        }
    }
}
