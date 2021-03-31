package com.mobile.otrium.di.module.fragment

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.otrium.repo.ProfileRepo
import com.mobile.otrium.ui.user.ProfileFragment
import com.mobile.otrium.ui.user.ProfilePresenter
import dagger.Module
import dagger.Provides

/*
* @module ProfileFragmentModule
* */
@Module
class ProfileFragmentModule {

    // Factory instance
    class Factory internal constructor(private val repo: ProfileRepo) :
        ViewModelProvider.NewInstanceFactory() {
        @NonNull
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfilePresenter(repo) as T
        }
    }

    // ProfilePresenter provide
    @Provides
    fun providePresenter(
        fragment: ProfileFragment,
        repo: ProfileRepo
    ): ProfilePresenter {
        return ViewModelProvider(fragment,Factory(repo)).get(ProfilePresenter::class.java)
    }
}