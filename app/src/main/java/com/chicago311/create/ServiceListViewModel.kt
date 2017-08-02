package com.chicago311.create

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.chicago311.api.ServiceRequest
import com.chicago311.repository.ServiceRequestRepository
import javax.inject.Inject

class ServiceListViewModel @Inject constructor(private val repository: ServiceRequestRepository) : ViewModel() {

    private val availableServices = MediatorLiveData<List<ServiceRequest>>()

    fun getApiResponse(): LiveData<List<ServiceRequest>> {
        return availableServices
    }

    fun loadAvailableServices(): LiveData<List<ServiceRequest>> {
        availableServices.addSource(
                repository.getAvailableServices(),
                { newData -> availableServices.setValue(newData) }
        )
        return availableServices
    }
}