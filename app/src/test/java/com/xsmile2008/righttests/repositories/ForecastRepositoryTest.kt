package com.xsmile2008.righttests.repositories

import com.nhaarman.mockitokotlin2.*
import com.xsmile2008.righttests.BaseTest
import com.xsmile2008.righttests.entities.MainWeatherInfo
import com.xsmile2008.righttests.entities.Weather
import com.xsmile2008.righttests.entities.Wind
import com.xsmile2008.righttests.network.responses.WeatherResponse
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class ForecastRepositoryTest : BaseTest() {

    companion object {

        const val LOCATION = "Kyiv,ua"

        val weatherResponse = WeatherResponse(
                1,
                MainWeatherInfo(
                        25.0,
                        1000.0,
                        1000.0,
                        20.0,
                        30.0
                ),
                listOf(
                        Weather(
                                1,
                                "weather",
                                "description",
                                "icon"
                        ),
                        Weather(
                                2,
                                "weather",
                                "description",
                                "icon"
                        ),
                        Weather(
                                3,
                                "weather",
                                "description",
                                "icon"
                        )
                ),
                Wind(
                        100.0,
                        90.0
                ),
                mapOf(
                        "rain" to 1
                ),
                mapOf(
                        "cloud" to 1
                )
        )
    }

    private val repository by lazy { ForecastRepository(apiClient, dispatchersProvider, spCache) }

    @Test
    fun check_fetchForecast_success() = runBlocking<Unit> {
        //Setup
        doReturn(LOCATION).whenever(spCache).location
        val call: Call<*> = mock()
        doReturn(call).whenever(weatherService).getCurrentWeather(any())
        doReturn(Response.success(weatherResponse)).whenever(call).execute()

        //Run
        assertEquals(weatherResponse, repository.fetchForecast())

        //Verify
        verify(spCache).location
        verify(apiClient).weatherService
        verify(weatherService).getCurrentWeather(LOCATION)
    }

    @Test
    fun check_fetchForecast_500() = runBlocking<Unit> {
        //Setup
        doReturn(LOCATION).whenever(spCache).location
        val call: Call<*> = mock()
        doReturn(call).whenever(weatherService).getCurrentWeather(any())
        doReturn(Response.error<ResponseBody>(500, mock())).whenever(call).execute()

        //Run
        try {
            repository.fetchForecast()
            fail()
        } catch (e: Exception) {
            assertTrue(e is HttpException && e.code() == 500)
        }

        //Verify
        verify(spCache).location
        verify(apiClient).weatherService
        verify(weatherService).getCurrentWeather(LOCATION)
    }
}