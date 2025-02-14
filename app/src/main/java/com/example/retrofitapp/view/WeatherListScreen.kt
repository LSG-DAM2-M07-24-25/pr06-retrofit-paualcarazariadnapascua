package com.example.retrofitapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.retrofitapp.ui.components.WeatherItem
import com.example.retrofitapp.viewmodel.WeatherViewModel

@Composable
fun WeatherListScreen(viewModel: WeatherViewModel) {
    val isLoading by viewModel.loading.observeAsState(true)
    val weatherData by viewModel.weatherData.observeAsState()

    if (isLoading) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary
            )
        }
    } else if (weatherData != null) {
        LazyColumn {
            items(listOf(weatherData!!)) { weather ->
                WeatherItem(weather)
            }
        }
    }
}
