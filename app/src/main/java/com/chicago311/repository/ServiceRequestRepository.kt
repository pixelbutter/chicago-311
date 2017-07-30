package com.chicago311.repository

import android.arch.lifecycle.LiveData
import com.chicago311.api.ServiceDetailsResponse
import com.chicago311.api.ServiceRequest

interface ServiceRequestRepository {
    fun getAvailableServices(): LiveData<List<ServiceRequest>>

    fun getServiceDetails(serviceCode: String): LiveData<ServiceDetailsResponse>
}