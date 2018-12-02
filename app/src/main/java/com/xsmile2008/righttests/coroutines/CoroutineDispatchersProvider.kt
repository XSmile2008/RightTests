package com.xsmile2008.righttests.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

open class CoroutineDispatchersProvider {

    open val Default: CoroutineDispatcher by lazy { Dispatchers.Default }
    open val Main: CoroutineDispatcher by lazy { Dispatchers.Main }
    open val IO: CoroutineDispatcher by lazy { Dispatchers.IO }
//    open val Unconfined: CoroutineDispatcher by lazy { Dispatchers.Unconfined }//TODO: experimental
}