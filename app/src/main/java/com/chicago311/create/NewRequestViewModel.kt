package com.chicago311.create

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.chicago311.data.model.ServiceDefinitionResponse
import com.chicago311.data.remote.ApiResponse
import com.chicago311.repository.ServiceRequestRepository
import javax.inject.Inject

// TODO use factory and pass in
class NewRequestViewModel @Inject constructor(val repository: ServiceRequestRepository) : ViewModel() {

    fun getServiceDefinition(serviceId: String): LiveData<ApiResponse<ServiceDefinitionResponse>> {
        return repository.getServiceDefinition(serviceId)
    }
}