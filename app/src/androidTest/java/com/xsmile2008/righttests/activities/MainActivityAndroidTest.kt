package com.xsmile2008.righttests.activities

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.IntentMatchers.anyIntent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.nhaarman.mockitokotlin2.*
import com.xsmile2008.righttests.BaseAndroidTest
import com.xsmile2008.righttests.R
import com.xsmile2008.righttests.entities.MainWeatherInfo
import com.xsmile2008.righttests.entities.Weather
import com.xsmile2008.righttests.entities.Wind
import com.xsmile2008.righttests.livedata.ViewAction
import com.xsmile2008.righttests.network.responses.WeatherResponse
import com.xsmile2008.righttests.viewmodels.MainViewModel
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class MainActivityAndroidTest : BaseAndroidTest() {

    companion object {

        val weatherResponse = WeatherResponse(
                id = 1,
                main = MainWeatherInfo(
                        temp = 25.0,
                        humidity = 1000.0,
                        pressure = 1000.0,
                        tempMin = 20.0,
                        tempMax = 30.0
                ),
                weather = listOf(
                        Weather(
                                id = 1,
                                main = "weather",
                                description = "description",
                                icon = "icon"
                        ),
                        Weather(
                                id = 2,
                                main = "weather",
                                description = "description",
                                icon = "icon"
                        ),
                        Weather(
                                id = 3,
                                main = "weather",
                                description = "description",
                                icon = "icon"
                        )
                ),
                wind = Wind(
                        speed = 100.0,
                        degree = 90.0
                ),
                rain = mapOf(
                        "rain" to 1
                ),
                clouds = mapOf(
                        "cloud" to 1
                )
        )
    }

    @get:Rule
    val activityRule = IntentsTestRule<MainActivity>(MainActivity::class.java, true, false)

    @get:Rule
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    //region LiveData

    private val viewAction = MutableLiveData<ViewAction>()
    private val showSpinner = MutableLiveData<Boolean>()
    private val weatherData = MutableLiveData<WeatherResponse>()
    //endregion

    private val viewModel: MainViewModel = mock {
        on { viewAction } doReturn viewAction
        on { showSpinner } doReturn showSpinner
        on { weatherData } doReturn weatherData
    }

    @Before
    override fun before() {
        super.before()

        doReturn(viewModel).whenever(viewModelFactory).create(MainViewModel::class.java)

        activityRule.launchActivity(null)//Launch activity AFTER setup viewModel
    }

    @After
    fun after() {
        assertNoUnverifiedIntents()
    }

    @Test
    fun check_changeLocationClicked() {
        //Run
        onView(withId(R.id.btn_change_location)).perform(click())

        //Verify
        verify(viewModel).onChangeLocationClicked()
    }

    @Test
    fun check_locationResponseText() {
        //Verify
        onView(withId(R.id.txt_main)).check(matches(withText(getResources().getString(R.string.loading))))

        //Run 2
        weatherData.postValue(weatherResponse)

        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)

        //Verify 2
        onView(withId(R.id.txt_main)).check(matches(withText(weatherResponse.toString())))
    }

    @Test
    fun check_locationResponseText_null() {
        //Verify
        onView(withId(R.id.txt_main)).check(matches(withText(getResources().getString(R.string.loading))))

        //Run 2
        weatherData.postValue(null)

        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)

        //Verify 2
        onView(withId(R.id.txt_main)).check(matches(withText(getResources().getString(R.string.no_data))))
    }

    @Test
    fun check_startActivityForResult() {
        //Setup
        intending(anyIntent()).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, Intent()))

        //Run
        viewAction.postValue(ViewAction.Navigate(LocationActivity::class.java, LocationActivity.REQUEST_CODE))

        //Verify
        intended(allOf(
                hasComponent(LocationActivity::class.java.name)
                //TODO: Can't verify request code here. But we can verify it in next line.
        ))
        verify(viewModel).onActivityResult(eq(LocationActivity.REQUEST_CODE), eq(Activity.RESULT_OK), any())
    }
}