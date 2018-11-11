package com.xsmile2008.righttests.dagger.components

import com.xsmile2008.righttests.dagger.modules.NetworkModule
import com.xsmile2008.righttests.dagger.modules.SystemModule
import dagger.Component

@Component(modules = [SystemModule::class, NetworkModule::class])
interface AppComponent
