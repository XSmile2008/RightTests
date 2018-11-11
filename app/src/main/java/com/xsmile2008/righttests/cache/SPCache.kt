package com.xsmile2008.righttests.cache

import android.content.SharedPreferences

class SPCache(private val sharedPreferences: SharedPreferences) {

    companion object {

        const val PREF_CITY = "PREF_CITY"
    }

    var city: String? = null
    set(value) {
        if (sharedPreferences.edit().putString(PREF_CITY, value).commit()) {
            field = value
        }
    }

    init {
        city = sharedPreferences.getString(PREF_CITY, null)
    }
}
