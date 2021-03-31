package com.mobile.otrium.di.module.app

import android.app.Application
import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import com.mobile.otrium.di.auth.AuthInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun getContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideCacheFile(context: Context): File {
        return File(context.cacheDir, "apolloCache")
    }

    @Singleton
    @Provides
    fun provideCacheStore(file: File): DiskLruHttpCacheStore {
        return DiskLruHttpCacheStore(file, 1024 * 1024)
    }

    @Singleton
    @Provides
    fun provideAuthorizationInterceptor(context: Context): AuthInterceptor {
        return AuthInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideApolloClient(
        authInterceptor: AuthInterceptor,
        cacheStore: DiskLruHttpCacheStore
    ): ApolloClient {
        return ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .build()
            )
            .httpCache(ApolloHttpCache(cacheStore))
            .build()
    }
}