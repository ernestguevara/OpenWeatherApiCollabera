package com.ernestguevara.openweatherapicollabera.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MainDTO(
    @SerializedName("feels_like")
    val feelsLike: Double? = null,
    val humidity: Int? = null,
    val pressure: Int? = null,
    val temp: Double? = null,
    @SerializedName("temp_max")
    val tempMax: Double? = null,
    @SerializedName("temp_min")
    val tempMin: Double? = null
)