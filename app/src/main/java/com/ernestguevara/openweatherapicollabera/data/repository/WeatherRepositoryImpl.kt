package com.ernestguevara.openweatherapicollabera.data.repository

import com.ernestguevara.openweatherapicollabera.data.local.WeatherDao
import com.ernestguevara.openweatherapicollabera.data.local.WeatherEntity
import com.ernestguevara.openweatherapicollabera.data.remote.WeatherApiService
import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel
import com.ernestguevara.openweatherapicollabera.domain.repository.WeatherRepository
import com.ernestguevara.openweatherapicollabera.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiService,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    /*
    API Call Section
     */
    override fun getWeather(): Flow<Resource<List<WeatherModel>>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertWeatherToDb(weatherModel: WeatherModel) {
        weatherDao.insertWeatherEntry(weatherModel.toEntity())
    }

    override suspend fun deleteWeatherToDb(weatherModel: WeatherModel) {
        weatherDao.deleteWeatherEntry(weatherModel.toEntity())
    }

    override fun getWeatherHistory(): Flow<List<WeatherEntity>> {
        return weatherDao.getAllWeatherEntry()
    }

    /*
    Database Call Section
    */
}