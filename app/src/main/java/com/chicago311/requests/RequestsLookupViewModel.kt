package com.chicago311.requests

import android.arch.lifecycle.ViewModel
import com.chicago311.repository.ServiceRequestRepository
import javax.inject.Inject

class RequestsLookupViewModel @Inject constructor(repository: ServiceRequestRepository) : ViewModel() {

    private val recentRequests = repository.getRecentRequests()

    fun getRecentRequests() = recentRequests
}