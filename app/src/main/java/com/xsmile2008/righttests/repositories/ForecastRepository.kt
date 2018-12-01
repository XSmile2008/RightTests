package com.xsmile2008.righttests.repositories

import com.xsmile2008.righttests.extensions.await
import com.xsmile2008.righttests.network.ApiClient
import com.xsmile2008.righttests.network.responses.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ForecastRepository @Inject constructor(private val apiClient: ApiClient) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    fun fetchForecast(): Deferred<WeatherResponse> = async {
        apiClient.weatherService.getCurrentWeather("Cherkasy,ua").await()
    }
}
