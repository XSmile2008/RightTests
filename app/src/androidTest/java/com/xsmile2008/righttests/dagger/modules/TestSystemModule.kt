package com.xsmile2008.righttests.dagger.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.squareup.moshi.Moshi
import com.xsmile2008.righttests.viewmodels.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestSystemModule(private val application: Application) {

    companion object {

        val moshi: Moshi = Moshi.Builder().build()
    }

    @Provides
    @Singleton
    internal fun provideApplication(): Application = spy(application)

    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context = spy(application.applicationContext)

    @Provides
    @Singleton
    internal fun provideDefaultSharedPreferences(): SharedPreferences = mock()

    @Provides
    @Singleton
    internal fun provideMoshi(): Moshi = moshi

    @Provides
    @Singleton
    internal fun provideViewModelFactory() = mock<ViewModelFactory>()

//    @Provides
//    @Singleton
//    internal fun provideViewModelFactory(
//            application: Application,
//            forecastRepository: ForecastRepository,
//            messageUtils: MessageUtils
//    ) = spy(
//            ViewModelFactory(
//                    application,
//                    forecastRepository,
//                    messageUtils
//            )
//    )
}
