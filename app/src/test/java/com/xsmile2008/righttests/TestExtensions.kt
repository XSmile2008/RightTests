package com.xsmile2008.righttests

import android.content.Context
import android.content.res.Resources
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyVararg
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import java.util.*

/**
 * Appliable only to mocked [Context]
 */
fun Context.mockStrings() {
    doAnswer { Arrays.toString(it.arguments) }.whenever(this).getString(any())
    doAnswer { Arrays.toString(it.arguments) }.whenever(this).getString(any(), anyVararg())
}

/**
 * Appliable only to mocked [Resources]
 */
fun Resources.mockStrings() {
    doAnswer { Arrays.toString(it.arguments) }.whenever(this).getString(any())
    doAnswer { Arrays.toString(it.arguments) }.whenever(this).getString(any(), anyVararg())
}
