package com.chicago311.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.chicago311.api.ServiceDetailsResponse
import com.chicago311.api.ServiceRequest
import com.chicago311.api.ServiceRequestService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ServiceRequestRepositoryImpl @Inject constructor(private val apiService: ServiceRequestService) : ServiceRequestRepository {

    override fun getAvailableServices(): LiveData<List<ServiceRequest>> {
        val liveData: MutableLiveData<List<ServiceRequest>> = MutableLiveData()
        val observable: Observable<List<ServiceRequest>> = apiService.getAvailableServices()

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    response ->
                    liveData.value = response
                }, {
                    error ->
                    error.printStackTrace() // todo timber
                })

        return liveData
    }

    override fun getServiceDetails(serviceCode: String): LiveData<ServiceDetailsResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}