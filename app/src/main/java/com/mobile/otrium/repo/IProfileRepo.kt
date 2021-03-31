package com.mobile.otrium.repo

import com.apollographql.apollo.api.Response
import com.mobile.otrium.ProfileQuery
import io.reactivex.rxjava3.core.Observable

/*
* Interface profile repository
* @fun getProfileInfo : get graphQL profile data
* */
interface IProfileRepo {

    fun getProfileInfo(): Observable<Response<ProfileQuery.Data>>
}