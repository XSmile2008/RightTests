package com.xsmile2008.righttests.repositories

import com.xsmile2008.righttests.cache.SPCache
import com.xsmile2008.righttests.extensions.await
import com.xsmile2008.righttests.network.ApiClient
import com.xsmile2008.righttests.network.responses.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class ForecastRepository @Inject constructor(
        private val apiClient: ApiClient,
        private val spCache: SPCache
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    var location
        get() = spCache.location
        set(value) {
            spCache.location = value
        }

    fun fetchForecast(): Deferred<WeatherResponse> = async {
        apiClient.weatherService.getCurrentWeather(location).await()
    }
}
