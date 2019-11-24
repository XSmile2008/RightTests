package com.xsmile2008.righttests.dagger.modules

import com.nhaarman.mockitokotlin2.mock
import com.xsmile2008.righttests.network.ApiClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
internal class TestNetworkModule {

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient = mock()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = mock()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = mock()
}