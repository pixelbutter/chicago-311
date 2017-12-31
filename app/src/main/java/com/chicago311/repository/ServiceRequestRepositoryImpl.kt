package com.chicago311.repository

import android.arch.lifecycle.LiveData
import com.chicago311.AppExecutors
import com.chicago311.data.Resource
import com.chicago311.data.local.ServiceRequestDatabase
import com.chicago311.data.local.ServicesDao
import com.chicago311.data.model.RequestDetails
import com.chicago311.data.model.RequestSummary
import com.chicago311.data.model.ServiceRequest
import com.chicago311.data.model.ServiceRequirementResponse
import com.chicago311.data.remote.ApiResponse
import com.chicago311.data.remote.ServiceRequestService
import timber.log.Timber
import javax.inject.Inject

class ServiceRequestRepositoryImpl @Inject constructor(
        private val appExecutors: AppExecutors,
        private val apiService: ServiceRequestService,
        private val db: ServiceRequestDatabase,
        private val dao: ServicesDao) : ServiceRequestRepository {

    override fun getAvailableServices(): LiveData<Resource<List<ServiceRequest>>> {
        return object : NetworkBoundResource<List<ServiceRequest>, List<ServiceRequest>>(appExecutors) {

            override fun saveCallResult(item: List<ServiceRequest>?) {
                db.beginTransaction()
                try {
                    if (item != null) dao.insertServices(item)
                    db.setTransactionSuccessful()
                } finally {
                    db.endTransaction()
                }
            }

            override fun shouldFetch(data: List<ServiceRequest>?): Boolean {
                val shouldLoad = data == null || data.isEmpty()
                Timber.d("Should load: $shouldLoad")
                return shouldLoad
            }

            override fun loadFromDb(): LiveData<List<ServiceRequest>> {
                Timber.d("Loading data from db")
                return dao.getAllServices()
            }

            override fun createCall(): LiveData<ApiResponse<List<ServiceRequest>>> {
                Timber.d("Making service call")
                return apiService.getAvailableServices()
            }
        }.asLiveData()
    }

    override fun getServiceSummary(serviceCode: String): LiveData<ServiceRequest> {
        return dao.getServiceSummary(serviceCode)
    }

    override fun getServiceRequirements(serviceCode: String): LiveData<ApiResponse<ServiceRequirementResponse>> {
        // TODO DAO?
        return apiService.getServiceDetails(serviceCode, true)
    }

    override fun getRequestDetails(requestId: String): LiveData<ApiResponse<List<RequestDetails>>> {
        return apiService.getRequestDetails(requestId)
    }

    override fun getRecentRequests(): LiveData<ApiResponse<List<RequestSummary>>> {
        return apiService.getRecentRequests()
    }
}