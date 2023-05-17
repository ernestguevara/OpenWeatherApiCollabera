package com.ernestguevara.openweatherapicollabera.data.remote.dto

import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel

data class WeatherDTO(
    val id: Int,
    val name: String? = null,
    val sysDTO: SysDTO? = null,
    val mainDTO: MainDTO? = null,
    val weatherResultsDTO: List<WeatherResultsDTO>? = null,
    val timezone: Int? = null,
    val dt: Int? = null,
    val windDTO: WindDTO? = null,
    val cloudsDTO: CloudsDTO? = null,
    val base: String? = null,
    val visibility: Int? = null,
    val cod: Int? = null,
    val coordDTO: CoordDTO? = null
) {
    fun dtoToDomainModel(): WeatherModel {
        return WeatherModel(
            id = id,
            name = name,
            sysDTO = sysDTO,
            mainDTO = mainDTO,
            weatherResultsDTO = weatherResultsDTO?.first()
        )
    }
}