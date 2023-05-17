package com.ernestguevara.openweatherapicollabera.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class WeatherDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var mockDb: WeatherDatabase

    private lateinit var dao: WeatherDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = mockDb.weatherDao
    }

    @After
    fun teardown() {
        mockDb.close()
    }

    @Test
    fun testInsertWeather() = runBlockingTest {
        //Prepare data
        val weather = provideWeatherItem()

        //Insert to mock database
        dao.insertWeatherEntry(weather)

        // Get db data
        val result = collectWeatherFlow(this, dao.getAllWeatherEntry())

        // Assert the expected value
        assertThat(result).contains(weather)
    }

    @Test
    fun testDeleteWeather() = runBlockingTest {
        //Prepare data
        val weather = provideWeatherItem()

        //Insert to mock database
        dao.insertWeatherEntry(weather)

        //Delete from mock database
        dao.deleteWeatherEntry(weather)

        // Get db data
        val result = collectWeatherFlow(this, dao.getAllWeatherEntry())

        // Assert the expected value
        assertThat(result).doesNotContain(weather)
    }

    @Test
    fun getWeatherHistory() = runBlockingTest {
        //Prepare data
        val weather = provideWeatherItem()

        //Insert to mock database
        dao.insertWeatherEntry(weather)

        // Get db data
        val result = collectWeatherFlow(this, dao.getAllWeatherEntry())

        // Assert the expected value
        assertThat(result).hasSize(1)
    }

    private fun provideWeatherItem(): WeatherEntity{
        return WeatherEntity(
            id = 1,
            cityId = 1,
            name = "testname",
            sysCountry ="PH",
            sysSunrise = 123213,
            sysSunset = 2131242,
            mainTemp = 21.0,
            mainTempMax = 23.0,
            mainTempMin = 25.0,
            weatherDescription = "test desc",
            weatherIcon = "04d",
            weatherId = 2,
            weatherMain = "testMain"
        )
    }

    private suspend fun collectWeatherFlow(
        scope: CoroutineScope,
        flow: Flow<List<WeatherEntity>>
    ): List<WeatherEntity> {
        val result = mutableListOf<WeatherEntity>()
        val job = scope.launch {
            try {
                flow.collect { data ->
                    result.addAll(data)
                }
                println("Flow collection completed")
            } catch (e: CancellationException) {
                println("Flow collection canceled")
            }
        }
        job.cancelAndJoin()
        return result
    }
}