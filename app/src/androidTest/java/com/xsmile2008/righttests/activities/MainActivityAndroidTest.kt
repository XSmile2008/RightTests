package com.xsmile2008.righttests.activities

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.xsmile2008.righttests.BaseAndroidTest
import com.xsmile2008.righttests.R
import com.xsmile2008.righttests.entities.MainWeatherInfo
import com.xsmile2008.righttests.entities.Weather
import com.xsmile2008.righttests.entities.Wind
import com.xsmile2008.righttests.livedata.ViewAction
import com.xsmile2008.righttests.network.responses.WeatherResponse
import com.xsmile2008.righttests.viewmodels.MainViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityAndroidTest : BaseAndroidTest() {

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
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java, true, false)

    @Mock
    lateinit var viewModel: MainViewModel

    //region LiveData

    @Mock
    lateinit var viewAction: MutableLiveData<ViewAction>

    @Mock
    lateinit var showSpinner: MutableLiveData<Boolean>

    @Mock
    lateinit var weatherData: MutableLiveData<WeatherResponse>
    //endregion

    @Before
    override fun before() {
        super.before()

//        MockitoAnnotations.initMocks(this)//You need to use this to init mocks marked with @Mock annotation in case you are not using MockitoJUnitRunner

        whenever(viewModel.viewAction).thenReturn(viewAction)
        whenever(viewModel.showSpinner).thenReturn(showSpinner)
        whenever(viewModel.weatherData).thenReturn(weatherData)

        //Alternative syntax to use with spies
//        doReturn(viewAction).`when`(viewModel).viewAction
//        doReturn(showSpinner).`when`(viewModel).showSpinner
//        doReturn(weatherData).`when`(viewModel).weatherData

        doReturn(viewModel).`when`(viewModelFactory).create(MainViewModel::class.java)

        activityRule.launchActivity(null)//Launch activity AFTER setup viewModel
    }

    @After
    override fun after() {
        super.after()
        //TODO:
//        verify(viewModel).viewAction
//        verify(viewModel).showSpinner
//        verify(viewModel).weatherData
//        verifyNoMoreInteractions(viewModel)
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

        Thread.sleep(5000)

        //Verify 2
        onView(withId(R.id.txt_main)).check(matches(withText(weatherResponse.toString())))
    }
}