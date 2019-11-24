package com.xsmile2008.righttests

import android.app.Application
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.xsmile2008.righttests.cache.SPCache
import com.xsmile2008.righttests.coroutines.TestCoroutineDispatchersProvider
import com.xsmile2008.righttests.network.ApiClient
import com.xsmile2008.righttests.network.services.WeatherService
import com.xsmile2008.righttests.utils.MessageUtils
import org.junit.After

abstract class BaseTest {

    open val autoverifyDefaultMocks: Boolean = true

    //region Mocked Retrofit services

    val weatherService: WeatherService = mock()
    //endregion

    //region Mocked dependencies

    val apiClient: ApiClient = mock {
        on { weatherService } doReturn weatherService
    }
    val application: Application = mock()
    val messageUtils: MessageUtils = mock()
    val spCache: SPCache = mock()
    //endregion

    protected val dispatchersProvider = TestCoroutineDispatchersProvider()

    private val mocks: MutableSet<Any> = mutableSetOf()
    private val defaultMocks = setOf(
            apiClient,
            application,
            messageUtils,
            spCache,
            weatherService
    )

    protected fun addToMocks(mock: Any) {
        mocks.add(mock)
    }

    protected inline fun <reified T : Any> T.addToVerifiedMocks(): T = apply { addToMocks(this) }

    protected inline fun <reified T : Any> autoverified(mock: T): T = mock.also { addToMocks(it) }

    @After
    fun autoverify() {
        if (mocks.isNotEmpty()) {
            verifyNoMoreInteractions(*mocks.toTypedArray())
        }
        if (autoverifyDefaultMocks) {
            verifyNoMoreInteractions(*defaultMocks.filter { it != application }.toTypedArray())
        }
    }
}