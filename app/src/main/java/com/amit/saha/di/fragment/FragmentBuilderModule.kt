package com.amit.saha.di.fragment

import com.amit.saha.ui.fragments.DetailsFragment
import com.amit.saha.ui.fragments.ListFactFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeListFactFragment(): ListFactFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): DetailsFragment
}
