package com.mobile.otrium.repo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx3.rxQuery
import com.mobile.otrium.ProfileQuery
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/*
* @constructor inject apolloClient
* */
class ProfileRepo @Inject constructor(private val apolloClient: ApolloClient) : IProfileRepo {

    override fun getProfileInfo(): Observable<Response<ProfileQuery.Data>> {

        val query = ProfileQuery()
        val observable = apolloClient.rxQuery(query)
        // Observable with Kotlin extension
        return observable.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread())
    }
}