package com.ernestguevara.openweatherapicollabera.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherEntry(weatherEntity: WeatherEntity)

    @Delete
    suspend fun deleteWeatherEntry(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM weather_items")
    fun getAllWeatherEntry(): Flow<List<WeatherEntity>>
}