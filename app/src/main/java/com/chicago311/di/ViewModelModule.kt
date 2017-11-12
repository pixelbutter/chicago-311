package com.chicago311.di

import android.arch.lifecycle.ViewModel
import com.chicago311.create.NewRequestViewModel
import com.chicago311.create.details.NewRequestDetailsViewModel
import com.chicago311.create.list.ServiceListViewModel
import com.chicago311.create.location.NewRequestLocationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ServiceListViewModel::class)
    abstract fun provideServiceListViewModel(viewModel: ServiceListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewRequestViewModel::class)
    abstract fun provideNewRequestViewModel(viewModel: NewRequestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewRequestDetailsViewModel::class)
    abstract fun provideNewRequestDetailsViewModel(viewModel: NewRequestDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewRequestLocationViewModel::class)
    abstract fun provideNewRequestLocationViewModel(viewModel: NewRequestLocationViewModel): ViewModel
}