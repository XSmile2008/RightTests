package com.xsmile2008.righttests.dagger.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SystemModule(private val application: Application) {

    companion object {

        val moshi: Moshi = Moshi.Builder().build()
    }

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideDefaultSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    internal fun provideMoshi(): Moshi = moshi
}
