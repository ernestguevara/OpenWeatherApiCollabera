package com.ernestguevara.openweatherapicollabera.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ernestguevara.openweatherapicollabera.R
import com.ernestguevara.openweatherapicollabera.data.local.WeatherDatabase
import com.ernestguevara.openweatherapicollabera.util.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext app: Context): Context {
        return app
    }

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_unavailable)
    )

    @Singleton
    @Provides
    fun provideWeatherDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, WeatherDatabase::class.java, DB_NAME).build()


    @Provides
    fun provideTimberTree(): Timber.Tree = Timber.DebugTree()
}