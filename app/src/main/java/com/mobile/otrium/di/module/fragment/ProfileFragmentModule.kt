package com.mobile.otrium.di.module.fragment

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.otrium.repo.ProfileRepo
import com.mobile.otrium.ui.contract.ProfileContract
import com.mobile.otrium.ui.view.ProfileFragment
import com.mobile.otrium.ui.presenter.ProfilePresenter
import dagger.Module
import dagger.Provides

/*
* @module ProfileFragmentModule
* */
@Module
class ProfileFragmentModule {

    // Factory instance
    class Factory internal constructor(private val repo: ProfileRepo,val view: ProfileContract.View) :
        ViewModelProvider.NewInstanceFactory() {
        @NonNull
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfilePresenter(repo, view) as T
        }
    }

    @Provides
    fun provideView(
        fragment: ProfileFragment
    ): ProfileContract.View {
        return fragment
    }

    // ProfilePresenter provide
    @Provides
    fun providePresenter(
        fragment: ProfileFragment,
        repo: ProfileRepo,
        view: ProfileContract.View
    ): ProfilePresenter {
        return ViewModelProvider(fragment,Factory(repo,view)).get(ProfilePresenter::class.java)
    }
}