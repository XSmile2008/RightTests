package com.xsmile2008.righttests

import com.xsmile2008.righttests.application.AppClass
import com.xsmile2008.righttests.dagger.components.AppComponent
import com.xsmile2008.righttests.dagger.components.DaggerTestAppComponent
import com.xsmile2008.righttests.dagger.modules.TestSystemModule

class TestAppClass: AppClass() {

    override fun buildDaggerComponent(): AppComponent {
        return DaggerTestAppComponent.builder().testSystemModule(TestSystemModule(this)).build()
    }
}