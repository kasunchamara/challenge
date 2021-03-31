package com.mobile.otrium.di.module.fragment

import com.mobile.otrium.ui.user.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilderModule {

    @ContributesAndroidInjector(
        modules = [
            ProfileFragmentModule::class
        ]
    )
    abstract fun contributeProfileFragment(): ProfileFragment
}