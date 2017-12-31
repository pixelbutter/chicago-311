package com.chicago311.repository

import android.arch.lifecycle.LiveData
import com.chicago311.data.Resource
import com.chicago311.data.model.RequestDetails
import com.chicago311.data.model.RequestSummary
import com.chicago311.data.model.ServiceRequest
import com.chicago311.data.model.ServiceRequirementResponse
import com.chicago311.data.remote.ApiResponse

interface ServiceRequestRepository {
    fun getAvailableServices(): LiveData<Resource<List<ServiceRequest>>>

    fun getServiceSummary(serviceCode: String): LiveData<ServiceRequest>

    fun getServiceRequirements(serviceCode: String): LiveData<ApiResponse<ServiceRequirementResponse>>

    fun getRequestDetails(requestId: String): LiveData<ApiResponse<List<RequestDetails>>>

    fun getRecentRequests(): LiveData<ApiResponse<List<RequestSummary>>>
}