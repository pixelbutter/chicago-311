package com.chicago311.repository

import android.arch.lifecycle.LiveData
import com.chicago311.AppExecutors
import com.chicago311.data.Resource
import com.chicago311.data.local.ServiceRequestDatabase
import com.chicago311.data.local.ServicesDao
import com.chicago311.data.model.ServiceDetailsResponse
import com.chicago311.data.model.ServiceRequest
import com.chicago311.data.remote.ApiResponse
import com.chicago311.data.remote.ServiceRequestService
import timber.log.Timber
import javax.inject.Inject

class ServiceRequestRepositoryImpl @Inject constructor(private val appExecutors: AppExecutors,
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
                Timber.d("Service list loaded from db: {$data}")
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<ServiceRequest>> {
                return dao.getAllServices()
            }

            override fun createCall(): LiveData<ApiResponse<List<ServiceRequest>>> {
                Timber.d("Making service call")
                return apiService.getAvailableServices()
            }
        }.asLiveData()
    }

    override fun getServiceDetails(serviceCode: String): LiveData<ServiceDetailsResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}