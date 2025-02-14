package com.example.retrofitapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitapp.api.Repository
import com.example.retrofitapp.model.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = Repository()

    val weatherData = MutableLiveData<WeatherResponse?>()
    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String?>()

    fun fetchWeather(city: String, apiKey: String) {
        loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getWeather(city, apiKey)
                if (response.isSuccessful) {
                    weatherData.value = response.body()
                } else {
                    errorMessage.value = "Error: ${response.code()} - ${response.message()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error de conexión: ${e.message}"
            } finally {
                loading.value = false
            }
        }
    }
}
