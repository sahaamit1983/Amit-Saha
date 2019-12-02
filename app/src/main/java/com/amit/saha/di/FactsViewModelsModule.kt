package com.amit.saha.di


import androidx.lifecycle.ViewModel
import com.amit.saha.ui.FactsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FactsViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(FactsViewModel::class)
    abstract fun bindAppViewModel(viewModel: FactsViewModel): ViewModel
}
