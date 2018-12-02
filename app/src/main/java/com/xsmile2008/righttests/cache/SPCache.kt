package com.xsmile2008.righttests.cache

import android.content.SharedPreferences
import com.xsmile2008.righttests.annotations.OpenClass
import javax.inject.Inject
import javax.inject.Singleton

@OpenClass
@Singleton
class SPCache @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {

        internal const val PREF_LOCATION = "PREF_LOCATION"
        internal const val DEFAULT_LOCATION = "Cherkasy,ua"
    }

    var location: String = sharedPreferences.getString(PREF_LOCATION, DEFAULT_LOCATION)!!
        set(value) {
            if (sharedPreferences.edit().putString(PREF_LOCATION, value).commit()) {
                field = value
            }
        }
}
