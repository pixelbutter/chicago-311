package com.chicago311.repository

import android.arch.lifecycle.LiveData
import com.chicago311.data.Resource
import com.chicago311.data.model.CityRequestDetails
import com.chicago311.data.model.CityRequestSummary
import com.chicago311.data.model.CityService
import com.chicago311.data.model.CityServiceRequirementResponse
import com.chicago311.data.remote.ApiResponse

interface ServiceRequestRepository {
    fun getAvailableServices(): LiveData<Resource<List<CityService>>>

    fun getServiceSummary(serviceCode: String): LiveData<CityService>

    fun getServiceRequirements(serviceCode: String): LiveData<ApiResponse<CityServiceRequirementResponse>>

    fun getRequestDetails(requestId: String): LiveData<ApiResponse<List<CityRequestDetails>>>

    fun getRecentRequests(): LiveData<ApiResponse<List<CityRequestSummary>>>
}