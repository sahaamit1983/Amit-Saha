package com.amit.saha.di

import android.app.Application
import com.amit.saha.FactsApplication

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuildersModule::class,
    ViewModelFactoryModule::class,
    FactsModule::class
])
interface FactsComponent : AndroidInjector<FactsApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): FactsComponent
    }
}
