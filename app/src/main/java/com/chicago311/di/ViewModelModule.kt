package com.chicago311.di

import android.arch.lifecycle.ViewModel
import com.chicago311.create.NewRequestViewModel
import com.chicago311.create.ServiceListViewModel
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
}