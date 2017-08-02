package com.chicago311.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.chicago311.ViewModelFactory
import com.chicago311.api.ServiceRequestService
import com.chicago311.create.ServiceListViewModel
import com.chicago311.repository.ServiceRequestRepository
import com.chicago311.repository.ServiceRequestRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    private val BASE_URL = "http://test311request.cityofchicago.org/open311/v2/"

    @Provides
    @Singleton
    fun provideServiceRequestService(): ServiceRequestService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ServiceRequestService::class.java)
    }

    @Provides
    @Singleton
    fun provideServiceRequestRepository(repository: ServiceRequestRepositoryImpl): ServiceRequestRepository {
        return repository
    }

    @Provides
    fun provideServiceListViewModel(viewModel: ServiceListViewModel): ViewModel {
        return viewModel
    }

    @Provides
    fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory {
        return factory
    }
}