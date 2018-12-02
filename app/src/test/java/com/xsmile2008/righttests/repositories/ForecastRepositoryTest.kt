package com.xsmile2008.righttests.repositories

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.xsmile2008.righttests.BaseTest
import com.xsmile2008.righttests.entities.MainWeatherInfo
import com.xsmile2008.righttests.entities.Weather
import com.xsmile2008.righttests.entities.Wind
import com.xsmile2008.righttests.network.responses.WeatherResponse
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
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

    @Before
    override fun before() {
        super.before()
    }

    @Test
    fun check_fetchForecast_success() {
        //Setup
        doReturn(LOCATION).whenever(spCache).location
        val call: Call<*> = mock(Call::class.java)
        doReturn(call).whenever(weatherService).getCurrentWeather(anyString())
        doReturn(Response.success(weatherResponse)).whenever(call).execute()

        //Run
        runBlocking {
            assertEquals(weatherResponse, repository.fetchForecast().await())
        }

        //Verify
        verify(weatherService).getCurrentWeather(LOCATION)
    }

    @Test
    fun check_fetchForecast_500() {
        //Setup
        doReturn(LOCATION).whenever(spCache).location
        val call: Call<*> = mock(Call::class.java)
        doReturn(call).whenever(weatherService).getCurrentWeather(anyString())
        doReturn(Response.error<ResponseBody>(500, mock(ResponseBody::class.java))).whenever(call).execute()

        //Run
        runBlocking {
            try {
                repository.fetchForecast().await()
                fail()
            } catch (e: Exception) {
                assertTrue(e is HttpException && e.code() == 500)
            }
        }

        //Verify
        verify(weatherService).getCurrentWeather(LOCATION)
    }
}