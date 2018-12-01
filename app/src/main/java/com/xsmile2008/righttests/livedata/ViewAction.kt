package com.xsmile2008.righttests.livedata

import android.content.Context
import android.content.Intent
import com.xsmile2008.righttests.activities.BaseActivity
import com.xsmile2008.righttests.extensions.toBundle

/**
 * A class used to wrap commands sent from ViewModel to its View
 */

sealed class ViewAction {

    companion object {

        /**
         * Arg with this name should be of type [Int]. If number of flags is bigger than one,
         * separate the flags with or operator
         */
        const val INTENT_FLAGS = "INTENT_FLAGS"
    }

    class Navigate @JvmOverloads constructor(
            @Suppress("MemberVisibilityCanBePrivate")
            val activityClass: Class<out BaseActivity>,
            val requestCode: Int? = null
    ) : ViewAction() {

        fun buildIntent(context: Context): Intent = Intent(context, activityClass).apply {
            getArg<Int>(INTENT_FLAGS)?.let { flags = it }
            putExtras(args.toBundle())
        }
    }

    class Finish @JvmOverloads constructor(val resultCode: Int? = null) : ViewAction()
    class Custom(val action: String) : ViewAction()

    @PublishedApi
    internal val args = HashMap<String, Any>()

    inline fun <reified T> getArg(key: String): T? {
        args.forEach { (iteratedKey, value) ->
            if (key == iteratedKey) {
                return value as? T
            }
        }
        return null
    }

    fun putArg(key: String, value: Any): ViewAction {
        args[key] = value
        return this
    }
}