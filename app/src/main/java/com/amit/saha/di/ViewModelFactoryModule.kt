package com.amit.saha.di

import androidx.lifecycle.ViewModelProvider
import com.amit.saha.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(providerFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}
