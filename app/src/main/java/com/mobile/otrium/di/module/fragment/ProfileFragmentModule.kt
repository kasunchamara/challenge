package com.mobile.otrium.di.module.fragment

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobile.otrium.repo.ProfileRepositoryImpl
import com.mobile.otrium.ui.user.ProfileFragment
import com.mobile.otrium.ui.user.ProfilePresenter
import dagger.Module
import dagger.Provides

@Module
class ProfileFragmentModule {

    class Factory internal constructor(val repo: ProfileRepositoryImpl) :
        ViewModelProvider.NewInstanceFactory() {
        @NonNull
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfilePresenter(repo) as T
        }
    }

    @Provides
    fun providePresenter(
        fragment: ProfileFragment,
        repo: ProfileRepositoryImpl
    ): ProfilePresenter {
        return ViewModelProvider(fragment,Factory(repo)).get(ProfilePresenter::class.java)
    }
}