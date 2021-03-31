package com.mobile.otrium.ui.contract

import androidx.lifecycle.MutableLiveData
import com.mobile.otrium.ProfileQuery

interface ProfileContract {

    interface View : BaseContract.View {
        fun getUserLiveData(data: MutableLiveData<ProfileQuery.Data>)
        fun getRefreshUserLiveData(data: MutableLiveData<ProfileQuery.Data>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getUserLiveData()
        fun getRefreshedUserLiveData()
    }
}