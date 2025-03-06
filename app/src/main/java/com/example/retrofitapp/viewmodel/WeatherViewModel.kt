package com.example.retrofitapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitapp.api.RetrofitInstance
import com.example.retrofitapp.model.WeatherEntity
import com.example.retrofitapp.model.toEntity
import com.example.retrofitapp.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherData = MutableLiveData<List<WeatherEntity>>() // 🔹 Ha de ser una Llista live data
    val weatherData: LiveData<List<WeatherEntity>> = _weatherData


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val apiKey = "59c3b2619ffb0c8f15d38d910cbb27f9"  // ✅ API Key aquí

    fun fetchWeather(city: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWeather(city, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { weatherResponse ->
                        val weatherEntity = weatherResponse.toEntity()
                        _weatherData.postValue(listOf(weatherEntity)) // ✅ Convertit en llista
                    }
                } else {
                    Log.e("API_ERROR", "Error API: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Error API: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    // ✅ FUNCIO CORRECTA PER FER UNA CRIDA DIRECTA
    suspend fun fetchWeatherDirect(city: String): WeatherEntity? {
        return withContext(Dispatchers.IO) {  // ✅ S'executa en el Dispatcher adequat
            try {
                val response = RetrofitInstance.api.getWeather(city, apiKey)
                if (response.isSuccessful) {
                    response.body()?.toEntity()
                } else {
                    Log.e("API_ERROR", "Error en la API: ${response.code()} - ${response.message()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Error en la API: ${e.message}")
                null
            }
        }
    }
}
