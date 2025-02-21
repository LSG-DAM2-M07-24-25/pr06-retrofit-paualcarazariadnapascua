package com.example.retrofitapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retrofitapp.model.WeatherEntity

@Composable
fun WeatherList(weatherData: List<WeatherEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(weatherData) { weather ->
            WeatherItem(weather)
        }
    }
}
