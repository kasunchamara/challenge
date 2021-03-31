package com.mobile.otrium.repo

import com.apollographql.apollo.api.Response
import com.mobile.otrium.ProfileQuery
import io.reactivex.rxjava3.core.Observable

interface ProfileRepository {

    fun getProfileInfo(): Observable<Response<ProfileQuery.Data>>
}