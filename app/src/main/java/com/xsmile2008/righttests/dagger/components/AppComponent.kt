package com.xsmile2008.righttests.dagger.components

import com.xsmile2008.righttests.activities.BaseActivity
import com.xsmile2008.righttests.dagger.modules.NetworkModule
import com.xsmile2008.righttests.dagger.modules.SystemModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SystemModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(activity: BaseActivity)
}
