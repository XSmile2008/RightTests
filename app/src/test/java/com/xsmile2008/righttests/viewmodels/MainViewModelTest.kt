package com.xsmile2008.righttests.viewmodels

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.whenever
import com.xsmile2008.righttests.BaseTest
import com.xsmile2008.righttests.activities.LocationActivity
import com.xsmile2008.righttests.entities.MainWeatherInfo
import com.xsmile2008.righttests.entities.Weather
import com.xsmile2008.righttests.entities.Wind
import com.xsmile2008.righttests.livedata.ViewAction
import com.xsmile2008.righttests.network.responses.WeatherResponse
import com.xsmile2008.righttests.repositories.ForecastRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class MainViewModelTest : BaseTest() {

    companion object {

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

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    //region Observers

    @Mock
    private lateinit var observerViewAction: Observer<ViewAction>

    @Mock
    private lateinit var observerShowSpinner: Observer<Boolean>

    @Mock
    private lateinit var observerWeatherData: Observer<WeatherResponse>
    //endregion

    @Mock
    lateinit var forecastRepository: ForecastRepository

    private val viewModel by lazy {
        MainViewModel(
                application,
                dispatchersProvider,
                forecastRepository,
                messageUtils
        )
    }

    @Before
    override fun before() {
        super.before()

        //Subscribe observers
        viewModel.viewAction.observeForever(observerViewAction)
        viewModel.showSpinner.observeForever(observerShowSpinner)
        viewModel.weatherData.observeForever(observerWeatherData)
    }

    @After
    override fun after() {
        super.after()
        verifyNoMoreInteractions(
                forecastRepository,
                observerViewAction,
                observerShowSpinner,
                observerWeatherData
        )
    }

    @Test
    fun check_onCreate_successfulLoadData() = runBlocking {
        //Setup
        doReturn(weatherResponse).whenever(forecastRepository).fetchForecast()

        //Run
        viewModel.onCreate()

        //Verify
        verify(observerShowSpinner).onChanged(true)
        verify(forecastRepository).fetchForecast()
        verify(observerWeatherData).onChanged(weatherResponse)
        verify(observerShowSpinner).onChanged(false)
    }

    @Test
    fun check_onCreate_noInternet() = runBlocking {
        //Setup
        val exception = RuntimeException("No internet")
        doThrow(exception).whenever(forecastRepository).fetchForecast()

        //Run
        viewModel.onCreate()

        //Verify
        verify(observerShowSpinner).onChanged(true)
        verify(forecastRepository).fetchForecast()
        verify(observerWeatherData).onChanged(null)
        verify(messageUtils).showError(exception)
        verify(observerShowSpinner).onChanged(false)
    }

    //region onActivityResult tests

    @Test
    fun check_onActivityResult_whenHandled() = runBlocking {
        //Setup
        doReturn(weatherResponse).whenever(forecastRepository).fetchForecast()

        //Run
        assertTrue(
                viewModel.onActivityResult(LocationActivity.REQUEST_CODE, Activity.RESULT_OK, null)
        )

        //Verify
        verify(observerShowSpinner).onChanged(true)
        verify(forecastRepository).fetchForecast()
        verify(observerWeatherData).onChanged(weatherResponse)
        verify(observerShowSpinner).onChanged(false)
    }

    @Test
    fun check_onActivityResult_RESULT_CANCELED() {
        //Run
        assertFalse(
                viewModel.onActivityResult(LocationActivity.REQUEST_CODE, Activity.RESULT_CANCELED, null)
        )
    }

    @Test
    fun check_onActivityResult_wrongRequestCode() {
        //Run
        assertFalse(
                viewModel.onActivityResult(-1, Activity.RESULT_OK, null)
        )
    }
    //endregion

    //region onChangeLocationClicked tests

    @Test
    fun check_onChangeLocationClicked() {
        //Run
        viewModel.onChangeLocationClicked()

        //Verify
        verify(observerViewAction).onChanged(
                //We overridden hashCode() and equals() for ViewAction.Navigate
                ViewAction.Navigate(
                        LocationActivity::class.java,
                        LocationActivity.REQUEST_CODE
                )
        )
    }

    @Test
    fun check_onChangeLocationClicked_v2() {
        //Run
        viewModel.onChangeLocationClicked()

        //Verify
        verify(observerViewAction).onChanged(
                //In case if we not overridden hashCode() and equals() for ViewAction.Navigate
                ArgumentMatchers.argThat {
                    it is ViewAction.Navigate
                            && it.activityClass == LocationActivity::class.java
                            && it.requestCode == LocationActivity.REQUEST_CODE
                            && it.args.isEmpty()
                }
        )
    }
    //endregion
}