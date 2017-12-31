package com.chicago311.create.list

import android.arch.lifecycle.ViewModel
import com.chicago311.repository.ServiceRequestRepository
import javax.inject.Inject

class ServiceListViewModel @Inject constructor(val repository: ServiceRequestRepository) : ViewModel() {

    private val availableServices = repository.getAvailableServices()

    fun getServices() = availableServices
}