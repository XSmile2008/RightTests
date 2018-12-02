package com.xsmile2008.righttests.viewmodels

import android.app.Activity
import android.app.Application
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xsmile2008.righttests.R
import com.xsmile2008.righttests.coroutines.CoroutineDispatchersProvider
import com.xsmile2008.righttests.livedata.ViewAction
import com.xsmile2008.righttests.repositories.ForecastRepository
import com.xsmile2008.righttests.utils.MessageUtils

class LocationViewModel(
        application: Application,
        coroutineDispatchersProvider: CoroutineDispatchersProvider,
        private val forecastRepository: ForecastRepository,
        private val messageUtils: MessageUtils
) : BaseViewModel(application, coroutineDispatchersProvider) {

    //region LiveData

    private val _location = MutableLiveData<String>()
    val location: LiveData<String> get() = _location
    //endregion

    init {
        _location.value = forecastRepository.location
    }

    //region View interaction

    @MainThread
    fun onSaveClicked(location: String) {
        if (location.isEmpty()) {
            messageUtils.showToast(
                    getApplication<Application>().getString(R.string.error_empty_location)
            )
        } else {
            forecastRepository.location = location
            _viewAction.value = ViewAction.Finish(Activity.RESULT_OK)
        }
    }
    //endregion
}