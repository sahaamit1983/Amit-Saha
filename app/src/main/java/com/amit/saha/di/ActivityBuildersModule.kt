package com.amit.saha.di

import com.amit.saha.di.fragment.FragmentBuilderModule
import com.amit.saha.ui.FactsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [
        FactsViewModelsModule::class,
        FragmentBuilderModule::class
    ])
    abstract fun contributeMainActivity(): FactsActivity
}
