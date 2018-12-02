package com.xsmile2008.righttests.network.responses

import com.squareup.moshi.JsonClass
import com.xsmile2008.righttests.entities.MainWeatherInfo
import com.xsmile2008.righttests.entities.Weather
import com.xsmile2008.righttests.entities.Wind

@JsonClass(generateAdapter = true)
data class WeatherResponse(
        val id: Long,
        val main: MainWeatherInfo,
        val weather: List<Weather>,
        val wind: Wind,
        val rain: Map<String, Int>?,
        val clouds: Map<String, Int>?
)