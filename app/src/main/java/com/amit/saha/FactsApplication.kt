package com.amit.saha

import com.amit.saha.di.DaggerFactsComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class FactsApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerFactsComponent.builder().application(this).build()
    }
}
