package com.ernestguevara.openweatherapicollabera.domain.model

import com.ernestguevara.openweatherapicollabera.data.local.WeatherEntity
import com.ernestguevara.openweatherapicollabera.data.remote.dto.*

data class WeatherModel(
    val id: Int,
    val name: String?,
    val sysDTO: SysDTO?,
    val mainDTO: MainDTO?,
    val weatherDTO: WeatherDTO?
) {
    fun toEntity(): WeatherEntity {
        return WeatherEntity(
        cityId = id,
        name = name,
        sysCountry = sysDTO?.country,
        sysSunrise = sysDTO?.sunrise,
        sysSunset = sysDTO?.sunset,
        mainTemp = mainDTO?.temp,
        mainTempMax = mainDTO?.tempMax,
        mainTempMin = mainDTO?.tempMin,
        weatherDescription = weatherDTO?.description,
        weatherIcon = weatherDTO?.icon,
        weatherId = weatherDTO?.id,
        weatherMain = weatherDTO?.main
        )
    }
}