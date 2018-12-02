package com.xsmile2008.righttests.coroutines

import kotlinx.coroutines.CoroutineDispatcher

class TestCoroutineDispatchersProvider : CoroutineDispatchersProvider() {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    override val Default: CoroutineDispatcher = testCoroutineDispatcher
    override val Main: CoroutineDispatcher = testCoroutineDispatcher
    override val IO: CoroutineDispatcher = testCoroutineDispatcher
}

