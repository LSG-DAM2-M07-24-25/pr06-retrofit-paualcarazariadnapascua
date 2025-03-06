package com.example.retrofitapp.repository


import android.util.Log
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
        return withContext(Dispatchers.IO) { // 👈 Cambiar a Dispatchers.IO
            try {
                Log.d("API_CALL", "Realizando solicitud a la API para la ciudad: $city")

                val response = apiRepository.getWeather(city, apiKey)

                Log.d("API_RESPONSE", "Código de respuesta: ${response.code()}")

                if (response.isSuccessful) {
                    response.body()?.let { weatherResponse ->
                        Log.d("API_SUCCESS", "Datos obtenidos correctamente: $weatherResponse")

                        val weatherEntity = WeatherEntity(
                            city = weatherResponse.name,
                            temperature = weatherResponse.main.temp,
                            humidity = weatherResponse.main.humidity,
                            description = weatherResponse.weather[0].description
                        )

                        insertWeather(weatherEntity) // Guarda en Room
                        return@withContext weatherEntity
                    }
                } else {
                    Log.e("API_ERROR", "Error en la API: ${response.code()} - ${response.message()}")
                    return@withContext null
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Excepción en la API: ${e.message}")
                return@withContext null
            }
        }
    }

    suspend fun getWeatherByCity(city: String): WeatherEntity? {
        return withContext(Dispatchers.IO) {
            weatherDao.getWeatherByCity(city) // ✅ Recupera de Room
        }
    }



}

