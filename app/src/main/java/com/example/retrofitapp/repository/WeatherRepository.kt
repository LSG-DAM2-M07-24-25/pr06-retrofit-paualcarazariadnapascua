package com.example.retrofitapp.repository


import com.example.retrofitapp.api.Repository
import com.example.retrofitapp.model.WeatherEntity
import com.example.retrofitapp.room.WeatherDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val weatherDao: WeatherDao,
    private val apiRepository: Repository
) {
    fun getAllWeatherData(): Flow<List<WeatherEntity>> {
        return weatherDao.getAllWeather()
    }

    suspend fun insertWeather(weather: WeatherEntity) {
        withContext(Dispatchers.IO) {
            weatherDao.insertWeather(weather)
        }
    }

    suspend fun fetchWeatherFromAPI(city: String, apiKey: String): WeatherEntity? {
        return withContext(Dispatchers.IO) {
            val response = apiRepository.getWeather(city, apiKey)
            if (response.isSuccessful) {
                response.body()?.let { weatherResponse ->
                    val weatherEntity = WeatherEntity(
                        city = weatherResponse.name,
                        temperature = weatherResponse.main.temp,
                        humidity = weatherResponse.main.humidity,
                        description = weatherResponse.weather[0].description
                    )
                    insertWeather(weatherEntity) // Guarda en Room
                    weatherEntity
                }
            } else {
                null
            }
        }
    }
}
