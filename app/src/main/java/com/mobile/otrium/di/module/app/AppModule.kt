package com.mobile.otrium.di.module.app

import android.app.Application
import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.mobile.otrium.di.auth.AuthInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton

/*
* @module project scope module
* */
@Module
class AppModule {

    @Singleton
    @Provides
    fun getContext(application: Application): Context {
        // Return context
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideCacheFile(context: Context): File {
        // Return cache directory file
        return File(context.cacheDir, "apolloCache")
    }

    @Singleton
    @Provides
    fun provideCacheStore(file: File): DiskLruHttpCacheStore {
        // Return cache store
        return DiskLruHttpCacheStore(file, 1024 * 1024)
    }

    @Singleton
    @Provides
    fun provideAuthorizationInterceptor(context: Context): AuthInterceptor {
        //Return auth interceptor
        return AuthInterceptor(context)
    }

    /*
    * Apollo Client object return
    * @param auth intercepter
    * @param cacheStore
    * */
    @Singleton
    @Provides
    fun provideApolloClient(
        authInterceptor: AuthInterceptor,
        cacheStore: DiskLruHttpCacheStore,
        context: Context
    ): ApolloClient {
        val sqlNormalizedCacheFactory = SqlNormalizedCacheFactory(context, "apollo.db")
        return ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .normalizedCache(sqlNormalizedCacheFactory)
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .build()
            )
            .httpCache(ApolloHttpCache(cacheStore))
            .build()
    }
}