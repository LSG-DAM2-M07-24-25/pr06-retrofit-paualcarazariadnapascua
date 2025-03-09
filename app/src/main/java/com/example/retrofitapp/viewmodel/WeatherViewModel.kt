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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherData = MutableLiveData<List<WeatherEntity>>()
    val weatherData: LiveData<List<WeatherEntity>> = _weatherData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _searchedText = MutableLiveData("")
    val searchedText: LiveData<String> = _searchedText

    private val _searchHistory = MutableLiveData<List<String>>(emptyList())
    val searchHistory: LiveData<List<String>> = _searchHistory

    private val _popularCitiesWeather = MutableLiveData<List<WeatherEntity>>()
    val popularCitiesWeather: LiveData<List<WeatherEntity>> = _popularCitiesWeather

    private val apiKey = "59c3b2619ffb0c8f15d38d910cbb27f9"  // ✅ API Key aquí

    private val popularCities = listOf(  // 🔹 Lista predefinida de ciudades
        "London", "New York", "Tokyo", "Madrid", "Paris",
        "Berlin", "Rome", "Los Angeles", "Sydney", "Moscow",
        "Toronto", "São Paulo", "Mexico City", "Seoul", "Dubai",
        "Beijing", "Hong Kong", "Bangkok", "Istanbul", "Singapore"
    )

    fun onSearchTextChange(text: String) {
        _searchedText.value = text
    }

    fun addToHistory(text: String) {
        if (text.isNotBlank() && !_searchHistory.value.orEmpty().contains(text)) {
            _searchHistory.value = listOf(text) + _searchHistory.value.orEmpty()
            _searchedText.value = ""
        }
    }

    fun clearHistory() {
        _searchHistory.value = emptyList()
    }

    fun fetchPopularCitiesWeather() {
        _isLoading.value = true
        viewModelScope.launch {
            val weatherList = mutableListOf<WeatherEntity>()
            for (city in popularCities) {
                val weather = fetchWeatherDirect(city)
                if (weather != null) {
                    weatherList.add(weather)
                }
            }
            _popularCitiesWeather.postValue(weatherList) // ✅ Actualiza LiveData con los datos de las ciudades populares
            _isLoading.postValue(false)
        }
    }

    fun fetchWeather(city: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWeather(city, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { weatherResponse ->
                        val weatherEntity = weatherResponse.toEntity()
                        _weatherData.postValue(listOf(weatherEntity)) // ✅ Guarda en LiveData
                        repository.insertWeather(weatherEntity) // 🔹 Guarda en Room
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

    suspend fun fetchWeatherDirect(city: String): WeatherEntity? {
        return withContext(Dispatchers.IO) {
            try {
                val cachedWeather = repository.getWeatherByCity(city)
                if (cachedWeather != null) {
                    return@withContext cachedWeather // ✅ Si está en Room, lo devuelve
                }

                val response = RetrofitInstance.api.getWeather(city, apiKey)
                if (response.isSuccessful) {
                    response.body()?.toEntity()?.also {
                        repository.insertWeather(it) // ✅ Guarda en Room
                    }
                } else {
                    Log.e("API_ERROR", "Error API: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Error API: ${e.message}")
            }
            null
        }
    }

    fun getSavedWeather(city: String) {
        viewModelScope.launch {
            val savedWeather = repository.getWeatherByCity(city) // 🔹 Carga de Room
            savedWeather?.let {
                _weatherData.postValue(listOf(it)) // ✅ Muestra el clima guardado
            }
        }
    }
}
