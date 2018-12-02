package com.xsmile2008.righttests.dagger.components

import com.xsmile2008.righttests.BaseAndroidTest
import com.xsmile2008.righttests.dagger.modules.TestNetworkModule
import com.xsmile2008.righttests.dagger.modules.TestSystemModule

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = [TestSystemModule::class, TestNetworkModule::class])
interface TestAppComponent : AppComponent {

    fun inject(test: BaseAndroidTest)
}