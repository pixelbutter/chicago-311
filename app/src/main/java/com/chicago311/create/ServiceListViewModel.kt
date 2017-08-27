package com.chicago311.create

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.chicago311.data.Resource
import com.chicago311.data.model.ServiceRequest
import com.chicago311.repository.ServiceRequestRepository
import javax.inject.Inject

internal class ServiceListViewModel @Inject constructor(
        val repository: ServiceRequestRepository) : ViewModel() {

    private val availableServices = repository.getAvailableServices()

    fun getServices(): LiveData<Resource<List<ServiceRequest>>> {
        return availableServices
    }
}