package com.xsmile2008.righttests.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xsmile2008.righttests.annotations.OpenClass
import com.xsmile2008.righttests.coroutines.CoroutineDispatchersProvider
import com.xsmile2008.righttests.livedata.SingleLiveEvent
import com.xsmile2008.righttests.livedata.ViewAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

@OpenClass
abstract class BaseViewModel(
        application: Application,
        protected val dispatchersProvider: CoroutineDispatchersProvider
) : AndroidViewModel(application), CoroutineScope {

    private val job: Job = SupervisorJob()
    override val coroutineContext: CoroutineContext = dispatchersProvider.Default + job

    //region LiveData

    @Suppress("PropertyName")
    protected val _viewAction = SingleLiveEvent<ViewAction>()
    val viewAction: LiveData<ViewAction> get() = _viewAction

    @Suppress("MemberVisibilityCanBePrivate", "PropertyName")
    protected val _showSpinner = MutableLiveData<Boolean>()
    val showSpinner: LiveData<Boolean> get() = _showSpinner
    //endregion

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
