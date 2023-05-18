package com.ernestguevara.openweatherapicollabera.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ernestguevara.openweatherapicollabera.data.local.WeatherEntity
import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel
import com.ernestguevara.openweatherapicollabera.domain.repository.WeatherRepository
import com.ernestguevara.openweatherapicollabera.util.Resource
import com.ernestguevara.openweatherapicollabera.util.getCurrentDayLong
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
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

    var userEmail: String = ""

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
                            it.apply {
                                localDate = getCurrentDayLong()
                                email = userEmail
                            }
                            _getWeatherValue.postValue(it)
                            Timber.i("ernesthor24 insert val ${Gson().toJson(it)}")
                            insertWeatherHistory(it)
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
        Timber.i("ernesthor24 weather userEmail $userEmail")
        weatherRepository.getWeatherHistory(userEmail)
            .onEach { result ->
                Timber.i("ernesthor24 weather history ${Gson().toJson(result)}")
                _getWeatherHistoryValue.value = result
            }.launchIn(viewModelScope)
    }

    fun setEmail(email: String) {
        userEmail = email
    }
}