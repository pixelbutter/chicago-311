package com.chicago311.repository

import android.arch.lifecycle.LiveData
import com.chicago311.data.Resource
import com.chicago311.data.model.ServiceDefinitionResponse
import com.chicago311.data.model.ServiceRequest
import com.chicago311.data.remote.ApiResponse

interface ServiceRequestRepository {
    fun getAvailableServices(): LiveData<Resource<List<ServiceRequest>>>

    fun getServiceDefinition(serviceCode: String): LiveData<ApiResponse<ServiceDefinitionResponse>>
}