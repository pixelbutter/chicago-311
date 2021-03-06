package com.chicago311.repository

import android.arch.lifecycle.LiveData
import com.chicago311.AppExecutors
import com.chicago311.data.Resource
import com.chicago311.data.local.ServiceRequestDatabase
import com.chicago311.data.local.ServicesDao
import com.chicago311.data.model.CityRequestDetails
import com.chicago311.data.model.CityRequestSummary
import com.chicago311.data.model.CityService
import com.chicago311.data.model.CityServiceRequirementResponse
import com.chicago311.data.remote.ApiResponse
import com.chicago311.data.remote.ServiceRequestService
import timber.log.Timber
import javax.inject.Inject

class ServiceRequestRepositoryImpl @Inject constructor(
        private val appExecutors: AppExecutors,
        private val apiService: ServiceRequestService,
        private val db: ServiceRequestDatabase,
        private val dao: ServicesDao) : ServiceRequestRepository {

    override fun getAvailableServices(): LiveData<Resource<List<CityService>>> {
        return object : NetworkBoundResource<List<CityService>, List<CityService>>(appExecutors) {

            override fun saveCallResult(item: List<CityService>?) {
                db.beginTransaction()
                try {
                    if (item != null) dao.insertServices(item)
                    db.setTransactionSuccessful()
                } finally {
                    db.endTransaction()
                }
            }

            override fun shouldFetch(data: List<CityService>?): Boolean {
                val shouldLoad = data == null || data.isEmpty()
                Timber.d("Should load: $shouldLoad")
                return shouldLoad
            }

            override fun loadFromDb(): LiveData<List<CityService>> {
                Timber.d("Loading data from db")
                return dao.getAllServices()
            }

            override fun createCall(): LiveData<ApiResponse<List<CityService>>> {
                Timber.d("Making service call")
                return apiService.getAvailableServices()
            }
        }.asLiveData()
    }

    override fun getServiceSummary(serviceCode: String): LiveData<CityService> {
        return dao.getServiceSummary(serviceCode)
    }

    override fun getServiceRequirements(serviceCode: String): LiveData<ApiResponse<CityServiceRequirementResponse>> {
        // TODO DAO?
        return apiService.getServiceDetails(serviceCode, true)
    }

    override fun getRequestDetails(requestId: String): LiveData<ApiResponse<List<CityRequestDetails>>> {
        return apiService.getRequestDetails(requestId)
    }

    override fun getRecentRequests(): LiveData<ApiResponse<List<CityRequestSummary>>> {
        return apiService.getRecentRequests()
    }
}