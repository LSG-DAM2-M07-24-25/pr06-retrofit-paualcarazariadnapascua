package com.example.retrofitapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitapp.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_table")
    fun getAllWeather(): Flow<List<WeatherEntity>>

    @Query("SELECT * FROM weather_table WHERE city = :city")
    suspend fun getWeatherByCity(city: String): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)
}
