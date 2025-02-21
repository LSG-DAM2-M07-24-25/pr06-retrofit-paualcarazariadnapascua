package com.example.retrofitapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitapp.model.WeatherEntity
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
                val newWeather = repository.fetchWeatherFromAPI(city, apiKey)
                if (newWeather != null) {
                    repository.insertWeather(newWeather)
                }

                repository.getAllWeatherData().collect { weatherList ->
                    _weatherData.postValue(weatherList)
                }

            } catch (e: Exception) {
                _errorMessage.postValue("Error de conexión: ${e.message}")
            } finally {
                _isLoading.postValue(false)  // ✅ Cuando termina, `isLoading` se pone en `false`
            }
        }
    }
}
