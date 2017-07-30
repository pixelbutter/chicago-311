package com.chicago311.create

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.chicago311.api.ServiceRequest
import com.chicago311.repository.ServiceRequestRepository
import com.chicago311.repository.ServiceRequestRepositoryImpl

class ServiceListViewModel : ViewModel() {

    private val availableServices: MediatorLiveData<List<ServiceRequest>>
    private val repository: ServiceRequestRepository

    init {
        availableServices = MediatorLiveData()
        repository = ServiceRequestRepositoryImpl()
    }

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