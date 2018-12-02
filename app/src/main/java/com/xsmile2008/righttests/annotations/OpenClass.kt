package com.xsmile2008.righttests.annotations

/**
 * See https://kotlinlang.org/docs/reference/compiler-plugins.html
 * See https://trickyandroid.com/using-mockito-with-kotlin/
 * TODO: make DebugOpenClass or/and TestOpenClass annotations
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class OpenClass