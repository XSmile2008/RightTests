package com.xsmile2008.righttests.application

import android.app.Application
import com.xsmile2008.righttests.dagger.components.DaggerAppComponent
import com.xsmile2008.righttests.dagger.modules.SystemModule

class AppClass: Application() {

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().systemModule(SystemModule(this)).build()
    }
}