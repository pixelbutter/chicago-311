package com.chicago311.di

import com.chicago311.create.NewRequestActivity
import com.chicago311.create.list.NewRequestListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(fragment: NewRequestListFragment)

    fun inject(activity: NewRequestActivity)
}