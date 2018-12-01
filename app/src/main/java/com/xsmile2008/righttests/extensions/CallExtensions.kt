package com.xsmile2008.righttests.extensions

import retrofit2.Call
import retrofit2.HttpException

//TODO: maybe rename those extensions to prevent confusion with coroutine's await

fun <T> Call<T>.await(): T {
    val response = execute()
    return when {
        response.isSuccessful -> response.body()!!
        else -> throw HttpException(response)
    }
}

fun <T> Call<T>.awaitNullable(): T? {
    val response = execute()
    return when {
        response.isSuccessful -> response.body()
        else -> throw HttpException(response)
    }
}