package com.xsmile2008.righttests

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import android.content.res.Resources
import androidx.test.platform.app.InstrumentationRegistry
import com.xsmile2008.righttests.application.AppClass
import com.xsmile2008.righttests.dagger.components.TestAppComponent
import com.xsmile2008.righttests.viewmodels.ViewModelFactory
import javax.inject.Inject

open class BaseAndroidTest {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    
    open fun before() {
        getComponent().inject(this)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun getComponent(): TestAppComponent {
        return AppClass.component as TestAppComponent
    }

    protected fun getApplicationContext(): Context {
        return getInstrumentation().targetContext.applicationContext
    }

    protected fun getApplication(): Application {
        return getApplicationContext() as Application
    }

    protected fun getResources(): Resources {
        return getApplicationContext().resources
    }

    protected fun getInstrumentation(): Instrumentation {
        return InstrumentationRegistry.getInstrumentation()
    }
}