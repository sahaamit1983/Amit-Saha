package com.amit.saha.di

import android.app.Application
import android.content.Context
import com.amit.saha.network.api.ApiService
import com.amit.saha.network.interceptor.NetworkInterceptor
import com.amit.saha.network.interceptor.RequestInterceptor
import com.amit.saha.repository.Repository
import com.amit.saha.ui.fragments.adapter.RecyclerAdapter
import com.amit.saha.util.Constant

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

@Module
object FactsModule {

    @Singleton
    @Provides
    public fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    public fun provideAdapter(): RecyclerAdapter {
        return RecyclerAdapter()
    }

    @Provides
    @Singleton
    public fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    public fun provideCache(application: Application): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

    @Provides
    @Singleton
    public fun provideNetworkInterceptor(application: Application): NetworkInterceptor {
        return NetworkInterceptor(application.applicationContext)
    }

    @Provides
    @Singleton
    public fun provideRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }

    @Provides
    @Singleton
    public fun provideOkhttpClient(cache: Cache, requestInterceptor: RequestInterceptor, networkInterceptor: NetworkInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor ()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.cache(cache)
        httpClient.addInterceptor(networkInterceptor)
        httpClient.addInterceptor(logging)
        httpClient.addNetworkInterceptor(requestInterceptor)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Singleton
    @Provides
    public fun provideRetrofitInstance(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    public fun apiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    public fun provideRepository(apiService: ApiService): Repository {
        return Repository(apiService)
    }
}
