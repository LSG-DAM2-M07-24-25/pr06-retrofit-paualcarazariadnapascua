package com.example.retrofitapp.room

import android.app.Application

class WeatherApplication : Application() {
    lateinit var database: WeatherDatabase
    lateinit var repository: WeatherRepository

    override fun onCreate() {
        super.onCreate()
        database = WeatherDatabase.getDatabase(this)
        repository = WeatherRepository(database.weatherDao(), Repository())
    }
}
