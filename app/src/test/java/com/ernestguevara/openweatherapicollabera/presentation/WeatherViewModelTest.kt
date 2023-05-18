package com.ernestguevara.openweatherapicollabera.presentation

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ernestguevara.openweatherapicollabera.MainCoroutineRule
import com.ernestguevara.openweatherapicollabera.data.MockWeatherRepository
import com.ernestguevara.openweatherapicollabera.domain.location.LocationTracker
import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel
import com.ernestguevara.openweatherapicollabera.domain.repository.WeatherRepository
import com.ernestguevara.openweatherapicollabera.getOrAwaitValue
import com.ernestguevara.openweatherapicollabera.presentation.main.WeatherViewModel
import com.ernestguevara.openweatherapicollabera.util.Resource
import com.ernestguevara.openweatherapicollabera.util.getCurrentDayLong
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mockWeatherRepository: MockWeatherRepository
    private lateinit var mockViewModel: WeatherViewModel

    @Mock
    private lateinit var locationTracker: LocationTracker

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockWeatherRepository = MockWeatherRepository()
        mockViewModel = WeatherViewModel(mockWeatherRepository, locationTracker)
    }

    /*
    API Test
     */
    @Test
    fun `should get weather from api`() = mainCoroutineRule.runBlockingTest {
        //Mock the real weather repository
        val apiMockRepository = Mockito.mock(WeatherRepository::class.java)

        //Prepare the response
        val response =
            Resource.Success(WeatherModel(id = 1, localDate = getCurrentDayLong(), email = ""))

        locationTracker.getCurrentLocation()?.let {
            whenever(apiMockRepository.getWeather(it)).thenReturn(flowOf(response))
        }

        mockViewModel.getWeather()

        val actualResults = mockViewModel.getWeatherValue.getOrAwaitValue()

        assertThat(response.data).isEqualTo(actualResults)
    }

    /*
    DB Test
     */
    @Test
    fun `should insert weather history`() = mainCoroutineRule.runBlockingTest {
        //Prepare Data
        val weatherItem = WeatherModel(cityId = 1)

        //Call the method ViewModel codes
        mockViewModel.insertWeatherHistory(weatherItem)

        //Get the values
        mockViewModel.getWeatherHistory()

        val expectedValue = mutableListOf<WeatherModel>()
        mockViewModel.getWeatherHistoryValue.getOrAwaitValue().apply {
            this.map {
                expectedValue.add(it.toWeatherModel())
            }
        }

        //Assert
        assertThat(expectedValue).contains(weatherItem)
    }

    @Test
    fun `should delete weather history`() = mainCoroutineRule.runBlockingTest {
        //Prepare Data
        val weatherItem = WeatherModel(cityId = 1)

        //Call the method ViewModel codes
        mockViewModel.insertWeatherHistory(weatherItem)
        mockViewModel.deleteWeatherHistory(weatherItem)

        //Get the values
        mockViewModel.getWeatherHistory()

        val expectedValue = mutableListOf<WeatherModel>()
        mockViewModel.getWeatherHistoryValue.getOrAwaitValue().apply {
            this.map {
                expectedValue.add(it.toWeatherModel())
            }
        }

        //Assert
        assertThat(expectedValue).doesNotContain(weatherItem)
    }

    @Test
    fun `should get all weather history`() = mainCoroutineRule.runBlockingTest {
        //Prepare Data
        val weatherItem = mutableListOf<WeatherModel>().apply {
            add(WeatherModel(cityId = 1))
            add(WeatherModel(cityId = 2))
            add(WeatherModel(cityId = 3))
        }

        weatherItem.forEach {
            mockViewModel.insertWeatherHistory(it)
        }

        //Call the method ViewModel codes
        mockViewModel.getWeatherHistory()

        val expectedValue = mockViewModel.getWeatherHistoryValue.getOrAwaitValue()

        // Assert the expected behavior
        // Additional Size since we add after initial init of API Call
        assertThat(expectedValue).hasSize(4)

    }
}