package com.ernestguevara.openweatherapicollabera.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ernestguevara.openweatherapicollabera.data.remote.dto.MainDTO
import com.ernestguevara.openweatherapicollabera.data.remote.dto.SysDTO
import com.ernestguevara.openweatherapicollabera.data.remote.dto.WeatherResultsDTO
import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel

@Entity(tableName = "weather_items")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var cityId: Int? = null,
    var name: String? = null,
    var sysCountry: String? = null,
    var sysSunrise: Int? = null,
    var sysSunset: Int? = null,
    var mainTemp: Double? = null,
    var mainTempMax: Double? = null,
    var mainTempMin: Double? = null,
    var weatherDescription: String? = null,
    var weatherIcon: String? = null,
    var weatherId: Int? = null,
    var weatherMain: String? = null
) {
    fun toWeatherModel(): WeatherModel {
        return WeatherModel(
            id = id,
            cityId = cityId,
            name = name,
            sysDTO = if (sysCountry != null && sysSunrise != null && sysSunset != null) {
                SysDTO(
                    country = sysCountry,
                    sunrise = sysSunrise,
                    sunset = sysSunset
                )
            } else {
                null
            },
            mainDTO = if (mainTemp != null && mainTempMax != null && mainTempMin != null) {
                MainDTO(
                    temp = mainTemp,
                    tempMax = mainTempMax,
                    tempMin = mainTempMin
                )
            } else {
                null
            },
            weatherResultsDTO = if (weatherDescription != null && weatherIcon != null && weatherId != null && weatherMain != null) {
                WeatherResultsDTO(
                    description = weatherDescription,
                    icon = weatherIcon,
                    id = weatherId,
                    main = weatherMain
                )
            } else {
                null
            }
        )
    }
}