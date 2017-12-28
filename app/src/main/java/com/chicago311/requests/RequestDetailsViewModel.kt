package com.chicago311.requests

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.chicago311.data.model.RequestDetails
import com.chicago311.data.remote.ApiResponse
import com.chicago311.repository.ServiceRequestRepository
import javax.inject.Inject

class RequestDetailsViewModel @Inject constructor(val repository: ServiceRequestRepository) : ViewModel() {

    private var requestId = MutableLiveData<String>()
    private val requestDetails = Transformations.switchMap(requestId, { repository.getRequestDetails(it) })

    fun getDetails(): LiveData<ApiResponse<List<RequestDetails>>> = requestDetails

    fun setRequestId(requestId: String) {
        val currentValue = this.requestId.value
        if (currentValue != requestId) {
            this.requestId.value = requestId
        }
    }
}