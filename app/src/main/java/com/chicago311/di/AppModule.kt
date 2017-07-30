package com.chicago311.di

import com.chicago311.ChicagoApplication
import com.chicago311.api.ServiceRequestService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule(val app: ChicagoApplication) {

    @Provides
    @Singleton
    fun provideApplication() = app

    @Singleton
    @Provides
    fun provideServiceRequestService(): ServiceRequestService {
        return Retrofit.Builder()
                .baseUrl("http://test311request.cityofchicago.org/open311/v2/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create<ServiceRequestService>(ServiceRequestService::class.java)
    }
}