package com.xsmile2008.righttests.network.services

import com.xsmile2008.righttests.network.responses.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    fun getCurrentWeather(@Query("q") location: String): Call<WeatherResponse>
}