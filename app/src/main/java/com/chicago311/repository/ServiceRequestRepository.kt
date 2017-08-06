package com.chicago311.repository

import android.arch.lifecycle.LiveData
import com.chicago311.data.Resource
import com.chicago311.data.model.ServiceDetailsResponse
import com.chicago311.data.model.ServiceRequest

interface ServiceRequestRepository {
    fun getAvailableServices(): LiveData<Resource<List<ServiceRequest>>>

    fun getServiceDetails(serviceCode: String): LiveData<ServiceDetailsResponse>
}