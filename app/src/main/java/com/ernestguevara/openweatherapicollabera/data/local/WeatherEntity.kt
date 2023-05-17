package com.ernestguevara.openweatherapicollabera.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ernestguevara.openweatherapicollabera.data.remote.dto.MainDTO
import com.ernestguevara.openweatherapicollabera.data.remote.dto.SysDTO
import com.ernestguevara.openweatherapicollabera.data.remote.dto.WeatherDTO
import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel

@Entity(tableName = "weather_items")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var cityId: Int,
    var name: String?,
    var sysCountry: String?,
    var sysSunrise: Int?,
    var sysSunset: Int?,
    var mainTemp: Double?,
    var mainTempMax: Double?,
    var mainTempMin: Double?,
    var weatherDescription: String?,
    var weatherIcon: String?,
    var weatherId: Int?,
    var weatherMain: String?
) {
    fun toWeatherModel(): WeatherModel {
        return WeatherModel(
            id = cityId,
            name = name,
            sysDTO = SysDTO(
                country = sysCountry,
                sunrise = sysSunrise,
                sunset = sysSunset,
                id = null,
                type = null
            ),
            mainDTO = MainDTO(
                temp = mainTemp,
                tempMax = mainTempMax,
                tempMin = mainTempMin,
                feelsLike = null,
                humidity = null,
                pressure = null
            ),
            weatherDTO = WeatherDTO(
                description = weatherDescription,
                icon = weatherIcon,
                id = weatherId,
                main = weatherMain
            )
        )
    }
}