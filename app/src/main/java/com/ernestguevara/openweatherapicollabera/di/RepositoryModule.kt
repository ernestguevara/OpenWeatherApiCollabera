package com.ernestguevara.openweatherapicollabera.di

import com.ernestguevara.openweatherapicollabera.data.local.WeatherDatabase
import com.ernestguevara.openweatherapicollabera.data.remote.WeatherApiService
import com.ernestguevara.openweatherapicollabera.data.repository.WeatherRepositoryImpl
import com.ernestguevara.openweatherapicollabera.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApiService, db: WeatherDatabase): WeatherRepository {
        return WeatherRepositoryImpl(api, db.weatherDao)
    }
}