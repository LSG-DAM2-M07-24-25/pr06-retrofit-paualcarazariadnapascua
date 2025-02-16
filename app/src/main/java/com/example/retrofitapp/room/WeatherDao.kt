package com.example.retrofitapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitapp.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather_table")
    fun getAllWeather(): Flow<List<WeatherEntity>>

    @Query("SELECT * FROM weather_table WHERE city = :city LIMIT 1")
    fun getWeatherByCity(city: String): Flow<WeatherEntity?>
}
