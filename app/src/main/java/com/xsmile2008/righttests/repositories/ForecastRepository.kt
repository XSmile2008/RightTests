package com.xsmile2008.righttests.repositories

import com.xsmile2008.righttests.network.ApiClient
import javax.inject.Inject

class ForecastRepository @Inject constructor(private val apiClient: ApiClient) {

//    fun fetchForecast(): Deferred<WeatherResponse> {
//        apiClient.weatherService.getCurrrentWeather("Cherkasy,ua").execute()
//    }
}
