package com.xsmile2008.righttests.viewmodels

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.xsmile2008.righttests.activities.LocationActivity
import com.xsmile2008.righttests.livedata.ViewAction
import com.xsmile2008.righttests.network.responses.WeatherResponse
import com.xsmile2008.righttests.repositories.ForecastRepository
import com.xsmile2008.righttests.utils.MessageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
        application: Application,
        private val forecastRepository: ForecastRepository,
        private val messageUtils: MessageUtils
) : BaseViewModel(application), LifecycleObserver {

    //region LiveData

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> get() = _weatherData
    //endregion

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        updateData()
    }

    private fun updateData() = launch {
        withContext(Dispatchers.Main) {
            _showSpinner.value = true
        }
        try {
            val weatherResponse = forecastRepository.fetchForecast().await()
            withContext(Dispatchers.Main) {
                _weatherData.value = weatherResponse
            }
        } catch (e: Exception) {
            messageUtils.showError(e)
        }
        withContext(Dispatchers.Main) {
            _showSpinner.value = false
        }
    }

    //region View interaction

    @Suppress("UNUSED_PARAMETER")
    @MainThread
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                LocationActivity.REQUEST_CODE -> {
                    updateData()
                    return true
                }
            }
        }
        return false
    }

    @MainThread
    fun onChangeLocationClicked() {
        _viewAction.value = ViewAction.Navigate(LocationActivity::class.java, LocationActivity.REQUEST_CODE)
    }
    //endregion
}