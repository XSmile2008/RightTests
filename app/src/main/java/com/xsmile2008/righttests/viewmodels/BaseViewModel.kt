package com.xsmile2008.righttests.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xsmile2008.righttests.livedata.SingleLiveEvent
import com.xsmile2008.righttests.livedata.ViewAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(
        application: Application
) : AndroidViewModel(application), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    //region LiveData

    private val _viewAction = SingleLiveEvent<ViewAction>()
    val viewAction = SingleLiveEvent<ViewAction>()

    @Suppress("MemberVisibilityCanBePrivate", "PropertyName")
    protected val _showSpinner = MutableLiveData<Boolean>()
    val showSpinner: LiveData<Boolean> get() = _showSpinner
    //endregion

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}
