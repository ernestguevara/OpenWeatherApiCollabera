package com.ernestguevara.openweatherapicollabera.data.remote

import com.ernestguevara.openweatherapicollabera.BuildConfig
import com.ernestguevara.openweatherapicollabera.data.remote.dto.WeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String? = "metric",
        @Query("appid") apiKey: String = BuildConfig.API_KEY
    ): WeatherDTO
}