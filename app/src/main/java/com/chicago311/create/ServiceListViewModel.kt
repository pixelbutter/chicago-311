package com.chicago311.create

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.chicago311.repository.ServiceRequestRepository
import com.chicago311.data.Resource
import com.chicago311.data.model.ServiceRequest
import javax.inject.Inject

class ServiceListViewModel @Inject constructor(repository: ServiceRequestRepository) : ViewModel() {

    private val availableServices: LiveData<Resource<List<ServiceRequest>>> = repository.getAvailableServices()

    fun getServices(): LiveData<Resource<List<ServiceRequest>>> {
        return availableServices
    }
}