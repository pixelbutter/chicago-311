package com.chicago311.di

import com.chicago311.create.BaseStepperFragment
import com.chicago311.create.NewRequestActivity
import com.chicago311.create.details.NewRequestDetailsViewModel
import com.chicago311.create.list.NewRequestListFragment
import com.chicago311.create.location.NewRequestLocationFragment
import com.chicago311.requests.RequestDetailsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(fragment: NewRequestListFragment)

    fun inject(fragment: BaseStepperFragment)

    fun inject(fragment: NewRequestDetailsViewModel)

    fun inject(fragment: NewRequestLocationFragment)

    fun inject(activity: NewRequestActivity)

    fun inject(activity: RequestDetailsActivity)
}