package com.xsmile2008.righttests.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes
import com.xsmile2008.righttests.R
import retrofit2.HttpException
import javax.inject.Inject


class MessageUtils @Inject constructor(private val context: Context) {

    @JvmOverloads
    fun showToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
        showToast(context.getString(message), duration)
    }

    @JvmOverloads
    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Handler(Looper.getMainLooper()).post { Toast.makeText(context, message, duration).show() }
    }

    fun showError(@StringRes message: Int) {
        showToast(message)
    }

    @JvmOverloads
    fun showError(message: String? = null) {
        showToast(message ?: context.getString(R.string.something_was_wrong))
    }

    fun showError(throwable: Throwable) {
        if (throwable is HttpException) {
            showError(throwable.message())
        } else {
            showError(throwable.message)
        }
        throwable.printStackTrace()
    }
}