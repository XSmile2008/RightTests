package com.xsmile2008.righttests.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.xsmile2008.righttests.BaseTest
import com.xsmile2008.righttests.R
import com.xsmile2008.righttests.livedata.ViewAction
import com.xsmile2008.righttests.repositories.ForecastRepository
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner.Silent::class)
class LocationViewModelTest : BaseTest() {

    companion object {
        const val LOCATION_KYIV = "Kyiv,ua"
        const val LOCATION_LVIV = "Lviv,ua"
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    //region Observers

    @Mock
    private lateinit var observerViewAction: Observer<ViewAction>

    @Mock
    private lateinit var observerShowSpinner: Observer<Boolean>

    @Mock
    private lateinit var observerLocation: Observer<String>
    //endregion

    @Mock
    lateinit var forecastRepository: ForecastRepository

    private val viewModel by lazy {
        LocationViewModel(
                application,
                dispatchersProvider,
                forecastRepository,
                messageUtils
        )
    }

    @Before
    override fun before() {
        super.before()
        //TODO: move this to BaseTest?
        doAnswer {
            Arrays.toString(it.arguments)
        }.whenever(application).getString(anyInt())
        doAnswer {
            Arrays.toString(it.arguments)
        }.whenever(application).getString(anyInt(), any())
    }

    @After
    override fun after() {
        super.after()
        verifyNoMoreInteractions(
                observerViewAction,
                observerShowSpinner,
                observerLocation
        )
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