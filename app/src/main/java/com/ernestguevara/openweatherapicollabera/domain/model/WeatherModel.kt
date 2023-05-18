package com.ernestguevara.openweatherapicollabera.domain.model

import com.ernestguevara.openweatherapicollabera.data.local.WeatherEntity
import com.ernestguevara.openweatherapicollabera.data.remote.dto.*

data class WeatherModel(
    val id: Int? = null,
    val cityId: Int? = null,
    val name: String? = null,
    val sysDTO: SysDTO? = null,
    val mainDTO: MainDTO? = null,
    val weatherResultsDTO: WeatherResultsDTO? = null,
    var localDate: Long? = null,
    var email: String? = null,
) {
    fun toEntity(): WeatherEntity {
        return WeatherEntity(
            cityId = cityId,
            name = name,
            sysCountry = sysDTO?.country,
            sysSunrise = sysDTO?.sunrise,
            sysSunset = sysDTO?.sunset,
            mainTemp = mainDTO?.temp,
            mainTempMax = mainDTO?.tempMax,
            mainTempMin = mainDTO?.tempMin,
            weatherDescription = weatherResultsDTO?.description,
            weatherIcon = weatherResultsDTO?.icon,
            weatherId = weatherResultsDTO?.id,
            weatherMain = weatherResultsDTO?.main,
            localDate = localDate,
            email = email
        )
    }
}