package com.xsmile2008.righttests.dagger.modules

import android.app.Application
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import com.xsmile2008.righttests.BuildConfig
import com.xsmile2008.righttests.network.ApiClient
import com.xsmile2008.righttests.network.interceptors.AuthInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
internal class TestNetworkModule {

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): ApiClient {
        return mock(ApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = mock(Retrofit::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient = mock(OkHttpClient::class.java)
}