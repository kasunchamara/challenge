package com.mobile.otrium.di.component

import android.app.Application
import com.mobile.otrium.app.BaseApplication
import com.mobile.otrium.di.module.activity.ActivityBuilderModule
import com.mobile.otrium.di.module.app.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/*
* AppComponent
* @modules Android support module
* @modules Activity builder module
* @modules App module
* */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        AppModule::class,
    ]
)

interface ApplicationComponent : AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}