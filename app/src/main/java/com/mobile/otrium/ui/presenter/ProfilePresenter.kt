package com.mobile.otrium.ui.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.otrium.ProfileQuery
import com.mobile.otrium.repo.ProfileRepo
import com.mobile.otrium.ui.contract.ProfileContract
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ProfilePresenter(private val repo: ProfileRepo, val view: ProfileContract.View) : ProfileContract.Presenter, ViewModel() {

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
                    }
                )
        )
    }

    override fun getUserLiveData() {
        view.getUserLiveData(userLiveData)
    }

    override fun getRefreshedUserLiveData() {
        view.getRefreshUserLiveData(refreshedUserLiveData)
    }
}