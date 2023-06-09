package com.ernestguevara.openweatherapicollabera.domain.repository

import android.location.Location
import com.ernestguevara.openweatherapicollabera.data.local.WeatherEntity
import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel
import com.ernestguevara.openweatherapicollabera.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    /*
    API Call Section
     */
    fun getWeather(location: Location): Flow<Resource<WeatherModel>>

    /*
    Database Call Section
     */
    suspend fun insertWeatherToDb(weatherModel: WeatherModel)
    suspend fun deleteWeatherToDb(weatherModel: WeatherModel)
    fun getWeatherHistory(email: String): Flow<List<WeatherEntity>>

}