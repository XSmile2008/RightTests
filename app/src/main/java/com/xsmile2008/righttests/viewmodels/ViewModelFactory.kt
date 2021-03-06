package com.xsmile2008.righttests.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xsmile2008.righttests.annotations.OpenClass
import com.xsmile2008.righttests.coroutines.CoroutineDispatchersProvider
import com.xsmile2008.righttests.repositories.ForecastRepository
import com.xsmile2008.righttests.utils.MessageUtils
import javax.inject.Inject
import javax.inject.Singleton

@OpenClass
@Singleton
class ViewModelFactory @Inject constructor(
        private val application: Application,
        private val coroutineDispatchersProvider: CoroutineDispatchersProvider,
        private val forecastRepository: ForecastRepository,
        private val messageUtils: MessageUtils
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MainViewModel::class.java) ->
            MainViewModel(
                    application = application,
                    coroutineDispatchersProvider = coroutineDispatchersProvider,
                    forecastRepository = forecastRepository,
                    messageUtils = messageUtils
            ) as T

        modelClass.isAssignableFrom(LocationViewModel::class.java) ->
            LocationViewModel(
                    application = application,
                    coroutineDispatchersProvider = coroutineDispatchersProvider,
                    forecastRepository = forecastRepository,
                    messageUtils = messageUtils
            ) as T

        else -> throw IllegalArgumentException()
    }
}