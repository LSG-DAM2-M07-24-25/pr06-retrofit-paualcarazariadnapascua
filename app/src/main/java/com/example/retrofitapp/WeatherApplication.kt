package com.example.retrofitapp

import android.app.Application
import com.example.retrofitapp.api.Repository
import com.example.retrofitapp.room.WeatherDatabase
import com.example.retrofitapp.repository.WeatherRepository

class WeatherApplication : Application() {
    lateinit var database: WeatherDatabase
    lateinit var repository: WeatherRepository

    override fun onCreate() {
        super.onCreate()
        database = WeatherDatabase.getDatabase(this)
        repository = WeatherRepository(database.weatherDao(), Repository())
    }
}
