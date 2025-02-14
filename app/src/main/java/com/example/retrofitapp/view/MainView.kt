package com.example.retrofitapp.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.retrofitapp.viewmodel.WeatherViewModel

@Composable
fun MainView() {
    val viewModel: WeatherViewModel = viewModel()
    WeatherListScreen(viewModel = viewModel)
}
