package com.chicago311.create

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.chicago311.data.model.ServiceRequest
import com.chicago311.data.model.ServiceRequirementResponse
import com.chicago311.data.remote.ApiResponse
import com.chicago311.repository.ServiceRequestRepository
import javax.inject.Inject

class NewRequestViewModel
@Inject constructor(val repository: ServiceRequestRepository) : ViewModel() {

    private var serviceCode: String = ""

    fun getServiceDefinition(): LiveData<ApiResponse<ServiceRequirementResponse>> {
        return repository.getServiceRequirements(serviceCode)
    }

    fun getServiceSummary(): LiveData<ServiceRequest> {
        return repository.getServiceSummary(serviceCode)
    }

    fun setId(serviceId: String) {
        if (this.serviceCode == serviceId) {
            return
        }
        this.serviceCode = serviceId
    }


}