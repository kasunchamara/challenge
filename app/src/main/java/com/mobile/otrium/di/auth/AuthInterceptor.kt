package com.mobile.otrium.di.auth

import android.content.Context
import com.mobile.otrium.R
import okhttp3.Interceptor
import okhttp3.Response

/*
*  authentication interceptor
*  add token
* */
class AuthInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${context.resources.getString(R.string.token)}")
            .build()

        return chain.proceed(request)
    }
}