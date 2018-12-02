package com.xsmile2008.righttests.application

import android.app.Application
import com.xsmile2008.righttests.dagger.components.AppComponent
import com.xsmile2008.righttests.dagger.components.DaggerAppComponent
import com.xsmile2008.righttests.dagger.modules.SystemModule

open class AppClass : Application() {

    companion object {

        lateinit var component: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        component = buildDaggerComponent()
    }

    protected open fun buildDaggerComponent(): AppComponent {
        return DaggerAppComponent.builder().systemModule(SystemModule(this)).build()
    }
}