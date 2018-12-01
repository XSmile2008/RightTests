package com.xsmile2008.righttests.application

import android.app.Application
import com.xsmile2008.righttests.dagger.components.AppComponent
import com.xsmile2008.righttests.dagger.components.DaggerAppComponent
import com.xsmile2008.righttests.dagger.modules.SystemModule

class AppClass : Application() {

    companion object {

        lateinit var component: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        setupDagger()
    }

    private fun setupDagger() {
        component = DaggerAppComponent.builder().systemModule(SystemModule(this)).build()
    }
}