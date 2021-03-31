package com.mobile.otrium.ui.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mobile.otrium.ProfileQuery
import com.mobile.otrium.repo.ProfileRepo
import com.mobile.otrium.ui.base.BasePresenter
import com.mobile.otrium.ui.contract.ProfileContract
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ProfilePresenter(private val repo: ProfileRepo) : BasePresenter<ProfileContract.View>() {

    private val TAG = "ProfilePresenter"

    private val mCompositeDisposable = CompositeDisposable()

    private val userLiveData: MutableLiveData<ProfileQuery.Data> = MutableLiveData()
    private val refreshedUserLiveData: MutableLiveData<ProfileQuery.Data> = MutableLiveData()

    fun getProfileData(isRefresh: Boolean) {
        mCompositeDisposable.add(
            repo.getProfileInfo()
                .doOnError {
                    userLiveData.postValue(null)
                }
                .subscribe(
                    {
                        if (!isRefresh)
                            userLiveData.postValue(it.data)
                        else
                            refreshedUserLiveData.postValue(it.data)
                    },
                    { t ->
                        if (!isRefresh)
                            userLiveData.postValue(null)
                        else
                            refreshedUserLiveData.postValue(null)
                        Log.e(TAG, t.message)
                    }
                )
        )
    }

//    override fun onCleared() {
//        mCompositeDisposable.clear()
//        super.onCleared()
//    }

    fun getUserLiveData(): MutableLiveData<ProfileQuery.Data> {
        return userLiveData
    }

    fun getRefreshedUserLiveData(): MutableLiveData<ProfileQuery.Data> {
        return refreshedUserLiveData
    }
}