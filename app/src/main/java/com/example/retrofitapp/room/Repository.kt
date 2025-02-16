package com.example.retrofitapp.room

import com.example.retrofitapp.api.RetrofitInstance
import com.example.retrofitapp.model.WeatherResponse
import retrofit2.Response

class Repository {
    suspend fun getWeather(city: String, apiKey: String): Response<WeatherResponse> {
        return RetrofitInstance.api.getWeather(city, apiKey)
    }
}
