package com.xsmile2008.righttests.network.interceptors

import com.xsmile2008.righttests.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
                chain.request().newBuilder().url(
                        chain.request().url().newBuilder()
                                .addQueryParameter("appid", BuildConfig.API_KEY)
                                .build()
                ).build()
        )
    }
}