package com.amit.saha.network.interceptor

import android.content.Context
import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (ConnectivityStatus.isConnected(context)) {
            request.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 60)
                    .build()

        } else {
            request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
        }

        return chain.proceed(request)
    }
}
