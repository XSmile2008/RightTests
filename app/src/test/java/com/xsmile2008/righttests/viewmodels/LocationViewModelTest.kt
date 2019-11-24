package com.xsmile2008.righttests.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.xsmile2008.righttests.BaseTest
import com.xsmile2008.righttests.R
import com.xsmile2008.righttests.livedata.ViewAction
import com.xsmile2008.righttests.mockStrings
import com.xsmile2008.righttests.repositories.ForecastRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class LocationViewModelTest : BaseTest() {

    companion object {
        const val LOCATION_KYIV = "Kyiv,ua"
        const val LOCATION_LVIV = "Lviv,ua"
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    //region Observers

    private val observerViewAction: Observer<ViewAction> = autoverified(mock())
    private val observerShowSpinner: Observer<Boolean> = autoverified(mock())
    private val observerLocation: Observer<String> = autoverified(mock())
    //endregion

    private val forecastRepository: ForecastRepository = autoverified(mock())

    private val viewModel by lazy {
        LocationViewModel(
                application,
                dispatchersProvider,
                forecastRepository,
                messageUtils
        )
    }

    @Before
    fun before() {
        application.mockStrings()//Custom extension. Look at it in details.
    }

    private fun setupObservers() {
        viewModel.viewAction.observeForever(observerViewAction)
        viewModel.showSpinner.observeForever(observerShowSpinner)
        viewModel.location.observeForever(observerLocation)
    }

    @Test
    fun keck_init() {
        //Setup, Run
        doReturn(LOCATION_LVIV).whenever(forecastRepository).location
        setupObservers()

        //Verify
        verify(forecastRepository).location
        verify(observerLocation).onChanged(LOCATION_LVIV)
    }

    @Test
    fun check_onSaveClicked() {
        //Setup, Run
        doReturn(LOCATION_LVIV).whenever(forecastRepository).location
        setupObservers()

        //Verify
        verify(forecastRepository).location
        verify(observerLocation).onChanged(LOCATION_LVIV)

        //Run 2
        viewModel.onSaveClicked(LOCATION_KYIV)

        //Verify 2
        verify(forecastRepository).location = LOCATION_KYIV
        verify(observerViewAction).onChanged(argThat {
            this is ViewAction.Finish && this.resultCode == android.app.Activity.RESULT_OK
        })
    }

    @Test
    fun check_onSaveClicked_emptyLocation() {
        //Setup, Run
        doReturn(LOCATION_LVIV).whenever(forecastRepository).location
        setupObservers()

        //Verify
        verify(forecastRepository).location
        verify(observerLocation).onChanged(LOCATION_LVIV)

        //Run 2
        viewModel.onSaveClicked("")

        //Verify 2
        val expected = application.getString(R.string.error_empty_location)
        verify(messageUtils).showToast(expected)
    }
}