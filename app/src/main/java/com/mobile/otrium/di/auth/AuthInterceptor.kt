package com.mobile.otrium.di.auth

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

/*
*  authentication interceptor
*  add token
* */
class AuthInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer f58815d510d5619d5b5030852cc0d2bdb8a7d8af")
            .build()

        return chain.proceed(request)
    }
}