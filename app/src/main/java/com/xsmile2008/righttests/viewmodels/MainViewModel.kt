package com.xsmile2008.righttests.viewmodels

import android.app.Application
import com.xsmile2008.righttests.repositories.ForecastRepository
import com.xsmile2008.righttests.utils.MessageUtils
import kotlinx.coroutines.launch

class MainViewModel(
        application: Application,
        private val forecastRepository: ForecastRepository,
        private val messageUtils: MessageUtils
) : BaseViewModel(application) {

    fun updateData() = launch {
        try {
            val weatherResponse = forecastRepository.fetchForecast().await()
        } catch (e: Exception) {
            messageUtils.showError(e)
        }
    }
}