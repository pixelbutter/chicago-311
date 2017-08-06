package com.chicago311

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.chicago311.create.ServiceListViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val serviceListViewModel: ServiceListViewModel) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        if (modelClass?.isAssignableFrom(ServiceListViewModel::class.java) == true) {
            return serviceListViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}