package com.xsmile2008.righttests.network

import com.xsmile2008.righttests.network.services.WeatherService
import retrofit2.Retrofit

class ApiClient(retrofit: Retrofit) {

    val weatherService: WeatherService = retrofit.create(WeatherService::class.java)
}