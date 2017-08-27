package com.chicago311.create

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.chicago311.repository.ServiceRequestRepository
import javax.inject.Inject

internal class NewRequestViewModel @Inject constructor(
        val repository: ServiceRequestRepository) : ViewModel() {

    private val serviceCode = MutableLiveData<String>()
    val serviceRequirements = Transformations.switchMap(serviceCode, {
        repository.getServiceRequirements(it)
    })
    val serviceSummary = Transformations.switchMap(serviceCode, {
        repository.getServiceSummary(it)
    })

    fun updateCode(serviceCode: String) {
        if (this.serviceCode.value != serviceCode) {
            this.serviceCode.value = serviceCode
        }
    }
}