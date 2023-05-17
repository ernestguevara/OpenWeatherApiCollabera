package com.ernestguevara.openweatherapicollabera.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestguevara.openweatherapicollabera.data.local.WeatherEntity
import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel
import com.ernestguevara.openweatherapicollabera.domain.repository.WeatherRepository
import com.ernestguevara.openweatherapicollabera.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _getWeatherValue = MutableLiveData<WeatherModel>()
    val getWeatherValue: MutableLiveData<WeatherModel> = _getWeatherValue

    private val _getWeatherError = MutableLiveData<String>()
    val getWeatherError: MutableLiveData<String> = _getWeatherError

    private val _getWeatherHistoryValue = MutableLiveData<List<WeatherEntity>>()
    val getWeatherHistoryValue: MutableLiveData<List<WeatherEntity>> = _getWeatherHistoryValue

    private var queryJob: Job? = null

    //init block to get weather upon creation
    init {
        getWeather()
    }

    fun getWeather() {
        queryJob?.cancel()
        queryJob = viewModelScope.launch {
            val data = weatherRepository.getWeather()

            data.onEach { results ->
                when (results) {
                    is Resource.Success -> {
                        results.data?.let {
                            _getWeatherValue.postValue(it)
                        }
                    }

                    is Resource.Error -> {
                        results.message?.let {
                            _getWeatherError.postValue(it)
                        }
                    }

                    is Resource.Loading -> {

                    }
                }
            }.launchIn(this)
        }
    }

    fun insertWeatherHistory(weatherModel: WeatherModel) = viewModelScope.launch {
        weatherRepository.insertWeatherToDb(weatherModel)
    }

    fun deleteWeatherHistory(weatherModel: WeatherModel) = viewModelScope.launch {
        weatherRepository.deleteWeatherToDb(weatherModel)
    }

    fun getWeatherHistory() = viewModelScope.launch {
        weatherRepository.getWeatherHistory()
            .onEach { result ->
                _getWeatherHistoryValue.value = result
            }.launchIn(viewModelScope)
    }

}