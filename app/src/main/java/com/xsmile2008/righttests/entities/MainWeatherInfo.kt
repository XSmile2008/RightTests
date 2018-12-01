package com.xsmile2008.righttests.entities

import com.squareup.moshi.Json

data class MainWeatherInfo(
        val temp: Double,
        val humidity: Double,
        val pressure: Double,
        @Json(name = "temp_min") val tempMin: Double,
        @Json(name = "temp_max") val tempMax: Double
)
