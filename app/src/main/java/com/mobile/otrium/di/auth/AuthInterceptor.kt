package com.mobile.otrium.di.auth

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer 80816d7ad52ff00e78222832f84251c555b9ab93")
            .build()

        return chain.proceed(request)
    }
}