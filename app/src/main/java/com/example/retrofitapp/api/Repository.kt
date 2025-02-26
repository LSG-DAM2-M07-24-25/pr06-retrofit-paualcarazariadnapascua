package com.example.retrofitapp.api

import WeatherResponse
import retrofit2.Response

class Repository {
    suspend fun getWeather(city: String, apiKey: String): Response<WeatherResponse> {
        return RetrofitInstance.api.getWeather(city, apiKey)
    }
}
