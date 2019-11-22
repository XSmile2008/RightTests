package com.xsmile2008.righttests.repositories

import com.xsmile2008.righttests.annotations.OpenClass
import com.xsmile2008.righttests.cache.SPCache
import com.xsmile2008.righttests.coroutines.CoroutineDispatchersProvider
import com.xsmile2008.righttests.extensions.await
import com.xsmile2008.righttests.network.ApiClient
import com.xsmile2008.righttests.network.responses.WeatherResponse
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@OpenClass
@Singleton
class ForecastRepository @Inject constructor(
        private val apiClient: ApiClient,
        dispatchersProvider: CoroutineDispatchersProvider,
        private val spCache: SPCache
) : CoroutineScope {

    override val coroutineContext: CoroutineContext = dispatchersProvider.IO + SupervisorJob()

    var location
        get() = spCache.location
        set(value) {
            spCache.location = value
        }

    suspend fun fetchForecast(): WeatherResponse = withContext(coroutineContext){
        apiClient.weatherService.getCurrentWeather(location).await()
    }
}
