package com.kidslox.app.utils.livedata

import androidx.lifecycle.MutableLiveData

/**
 * Perform checking if the value is valid and save it to prefs in checkValue callback.
 * In callback return whether the value is valid and LiveData should be updated.
 */
class SPCacheLiveData<R>(
    initValue: R,
    private val checkValue: (oldValue: R?, value: R?) -> Boolean,
    private val doOnSet: ((oldValue: R?, value: R?) -> Unit)? = null
) : MutableLiveData<R>() {

    /**
     * Needed to prevent calling checkValue and doOnSet in init
     */
    private var isInited = false
    private var oldValue: R? = null

    init {
        value = initValue
        observeForever {
            if (isInited) {
                doOnSet?.invoke(oldValue, it)
            } else {
                isInited = true
            }
            oldValue = it
        }
    }

    override fun setValue(newValue: R?) {
        if (isInited) {
            if (checkValue(value, newValue)) {
                super.setValue(newValue)
            }
        } else {
            super.setValue(newValue)
        }
    }

    override fun postValue(newValue: R) {
        if (checkValue(value, newValue)) {
            super.postValue(newValue)
        }
    }
}