package com.ernestguevara.openweatherapicollabera.data.remote.dto

data class WeatherResponse(
    val id: Int,
    val name: String?,
    val sysDTO: SysDTO?,
    val mainDTO: MainDTO?,
    val weatherDTO: List<WeatherDTO>?,
    val timezone: Int?,
    val dt: Int?,
    val windDTO: WindDTO?,
    val cloudsDTO: CloudsDTO?,
    val base: String?,
    val visibility: Int?,
    val cod: Int?,
    val coordDTO: CoordDTO?
)