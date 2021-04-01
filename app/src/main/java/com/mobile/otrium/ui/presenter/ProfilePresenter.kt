package com.mobile.otrium.ui.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.otrium.ProfileQuery
import com.mobile.otrium.repo.ProfileRepo
import com.mobile.otrium.ui.contract.ProfileContract
import io.reactivex.rxjava3.disposables.CompositeDisposable

/*
* Main presenter class - MVP architecture
* */
class ProfilePresenter(private val repo: ProfileRepo, val view: ProfileContract.View) : ProfileContract.Presenter, ViewModel() {

    private val mCompositeDisposable = CompositeDisposable()

    private val userLiveData: MutableLiveData<ProfileQuery.Data> = MutableLiveData()
    private val refreshedUserLiveData: MutableLiveData<ProfileQuery.Data> = MutableLiveData()

    // fetching the graphql profile data
    fun getProfileData(isRefresh: Boolean) {
        view.showProgress()
        mCompositeDisposable.add(
            repo.getProfileInfo()
                .doOnError {
                    userLiveData.postValue(null)
                }
                .subscribe(
                    {
                        view.hideProgress()
                        if (!isRefresh)
                            userLiveData.postValue(it.data)
                        else
                            refreshedUserLiveData.postValue(it.data)
                    },
                    {
                        view.hideProgress()
                        if (!isRefresh)
                            userLiveData.postValue(null)
                        else
                            refreshedUserLiveData.postValue(null)
                    }
                )
        )
    }

    // presenter get data and call the view method
    override fun getUserLiveData() {
        view.getUserLiveData(userLiveData)
    }

    // presenter get the refresh data and call the view method
    override fun getRefreshedUserLiveData() {
        view.getRefreshUserLiveData(refreshedUserLiveData)
    }
}