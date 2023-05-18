package com.ernestguevara.openweatherapicollabera.data.repository

import android.location.Location
import com.ernestguevara.openweatherapicollabera.data.local.WeatherDao
import com.ernestguevara.openweatherapicollabera.data.local.WeatherEntity
import com.ernestguevara.openweatherapicollabera.data.remote.WeatherApiService
import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel
import com.ernestguevara.openweatherapicollabera.domain.repository.WeatherRepository
import com.ernestguevara.openweatherapicollabera.util.Constants.ERROR_GENERIC
import com.ernestguevara.openweatherapicollabera.util.Constants.ERROR_HTTP
import com.ernestguevara.openweatherapicollabera.util.Constants.ERROR_IO
import com.ernestguevara.openweatherapicollabera.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApiService,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    /*
    API Call Section
     */
    override fun getWeather(location: Location): Flow<Resource<WeatherModel>> = flow {
        emit(Resource.Loading())

        try {
            val result = api.getWeather(location.latitude, location.longitude)
            emit(Resource.Success(result.dtoToDomainModel()))
        } catch (e: HttpException) {
            emit(Resource.Error(message = ERROR_HTTP))
        } catch (e: IOException) {
            emit(Resource.Error(message = ERROR_IO))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(message = ERROR_GENERIC))
        }
    }

    /*
    Database Call Section
    */

    override suspend fun insertWeatherToDb(weatherModel: WeatherModel) {
        weatherDao.insertWeatherEntry(weatherModel.toEntity())
    }

    override suspend fun deleteWeatherToDb(weatherModel: WeatherModel) {
        weatherDao.deleteWeatherEntry(weatherModel.toEntity())
    }

    override fun getWeatherHistory(email: String): Flow<List<WeatherEntity>> {
        return weatherDao.getAllWeatherEntry(email)
    }
}