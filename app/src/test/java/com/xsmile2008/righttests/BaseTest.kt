package com.xsmile2008.righttests

import android.app.Application
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.xsmile2008.righttests.cache.SPCache
import com.xsmile2008.righttests.coroutines.TestCoroutineDispatchersProvider
import com.xsmile2008.righttests.network.ApiClient
import com.xsmile2008.righttests.network.services.WeatherService
import com.xsmile2008.righttests.repositories.ForecastRepository
import com.xsmile2008.righttests.utils.MessageUtils
import org.mockito.Mock

abstract class BaseTest {

    //region Mocked dependencies

    @Mock
    lateinit var apiClient: ApiClient

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var messageUtils: MessageUtils

    @Mock
    lateinit var spCache: SPCache
    //endregion

    //region Mocked Retrofit services

    @Mock
    lateinit var weatherService: WeatherService
    //endregion

    protected val dispatchersProvider = TestCoroutineDispatchersProvider()

    open fun before() {
        doReturn(weatherService).whenever(apiClient).weatherService
    }

    open fun after() {

    }
}