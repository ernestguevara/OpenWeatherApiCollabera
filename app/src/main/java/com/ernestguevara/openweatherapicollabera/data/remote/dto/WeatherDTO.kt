package com.ernestguevara.openweatherapicollabera.data.remote.dto

import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel
import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    val id: Int,
    val name: String? = null,
    @SerializedName("sys")
    val sysDTO: SysDTO? = null,
    @SerializedName("main")
    val mainDTO: MainDTO? = null,
    @SerializedName("weather")
    val weatherResultsDTO: List<WeatherResultsDTO>? = null,
    val timezone: Int? = null,
    val dt: Int? = null,
    @SerializedName("wind")
    val windDTO: WindDTO? = null,
    @SerializedName("clouds")
    val cloudsDTO: CloudsDTO? = null,
    val base: String? = null,
    val visibility: Int? = null,
    val cod: Int? = null,
    @SerializedName("coord")
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