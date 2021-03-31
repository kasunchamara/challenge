package com.mobile.otrium.ui.contract

import androidx.lifecycle.MutableLiveData
import com.mobile.otrium.ProfileQuery

/*
* Profile contract 
* */
interface ProfileContract {

    interface View : BaseContract.View {
        /*
        * @View
        * get view profile live data
        * */
        fun getUserLiveData(data: MutableLiveData<ProfileQuery.Data>)

        /*
        * @View
        * get refresh view profile live data
        * */
        fun getRefreshUserLiveData(data: MutableLiveData<ProfileQuery.Data>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        /*
       * @Presenter
       * get graphql profile live data
       * */
        fun getUserLiveData()

        /*
        * @Presenter
        * get refresh graphql profile live data
        * */
        fun getRefreshedUserLiveData()
    }
}