package com.ernestguevara.openweatherapicollabera.data

import android.location.Location
import com.ernestguevara.openweatherapicollabera.data.local.WeatherEntity
import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel
import com.ernestguevara.openweatherapicollabera.domain.repository.WeatherRepository
import com.ernestguevara.openweatherapicollabera.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
Make class open so we Mockito can use it
We will just emulate the repository flow
 */
open class MockWeatherRepository : WeatherRepository {
    var weather = WeatherModel(id = 1)
    var savedWeather = mutableListOf<WeatherEntity>()

    /*
     * API Section
     */
    override fun getWeather(location: Location): Flow<Resource<WeatherModel>> {
        return flow { emit(Resource.Success(weather)) }
    }

    /*
     * DB Section
     */
    override suspend fun insertWeatherToDb(weatherModel: WeatherModel) {
        savedWeather.add(weatherModel.toEntity())
    }

    override suspend fun deleteWeatherToDb(weatherModel: WeatherModel) {
        savedWeather.remove(weatherModel.toEntity())
    }

    override fun getWeatherHistory(email: String): Flow<List<WeatherEntity>> {
        return flow { emit(savedWeather) }
    }
}