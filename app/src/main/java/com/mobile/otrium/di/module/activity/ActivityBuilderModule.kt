package com.mobile.otrium.di.module.activity

import com.mobile.otrium.di.module.fragment.MainFragmentBuilderModule
import com.mobile.otrium.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/*
* @modules Activity builder
* @contributesAndroidInjector MainFragmentBuilderModule
* @abstract MainActivity
* */
@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(
        modules = [
            MainFragmentBuilderModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}
