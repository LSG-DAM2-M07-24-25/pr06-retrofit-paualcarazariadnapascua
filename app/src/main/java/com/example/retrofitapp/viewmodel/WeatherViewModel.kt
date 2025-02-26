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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherData = MutableLiveData<List<WeatherEntity>>()
    val weatherData: LiveData<List<WeatherEntity>> = _weatherData

    private val _isLoading = MutableLiveData<Boolean>()  // ✅ Agregamos `isLoading`
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchWeather(city: String, apiKey: String) {
        _isLoading.value = true  // ✅ Indicamos que la carga ha comenzado

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWeather(city, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { weatherResponse ->
                        val weatherEntity = weatherResponse.toEntity()
                        _weatherData.postValue(listOf(weatherEntity))
                    } ?: Log.e("API_ERROR", "El cuerpo de la respuesta es null")
                } else {
                    Log.e("API_ERROR", "Error en la API: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Error en la API: ${e.message}")
            } finally {
                _isLoading.postValue(false)  // ✅ Indicamos que la carga ha terminado
            }
        }
    }




}


