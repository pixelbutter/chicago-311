package com.chicago311.di

import com.chicago311.ChicagoApplication
import com.chicago311.api.ServiceRequestService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: ChicagoApplication)

    fun getServiceRequestService(): ServiceRequestService
}