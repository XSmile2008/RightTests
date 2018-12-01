package com.xsmile2008.righttests.extensions

import com.squareup.moshi.Moshi

inline fun <reified T> Moshi.toJson(source: T?): String = this.adapter(T::class.java).toJson(source)

inline fun <reified T> Moshi.fromJson(source: String?): T? {
    return if (source == null || source.isEmpty()) {
        null
    } else {
        this.adapter(T::class.java).nullSafe().fromJson(source)
    }
}