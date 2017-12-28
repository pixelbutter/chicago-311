package com.chicago311.create.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.chicago311.create.NewRequestViewModel
import com.chicago311.data.model.ServiceRequest
import com.chicago311.data.model.ServiceRequestAttribute
import com.chicago311.data.model.ServiceRequirementResponse
import com.chicago311.data.remote.ApiResponse
import com.chicago311.repository.ServiceRequestRepository
import timber.log.Timber
import javax.inject.Inject

class NewRequestDetailsViewModel @Inject constructor(val repository: ServiceRequestRepository) : ViewModel() {

    private val requiredInputMap: MutableMap<String, List<String>?> = mutableMapOf()
    private val optionalInputMap: MutableMap<String, List<String>?> = mutableMapOf()
    private val serviceCode = MutableLiveData<String>()
    private val serviceRequirements = Transformations.switchMap(serviceCode, { repository.getServiceRequirements(it) })
    private val serviceSummary = Transformations.switchMap(serviceCode, { repository.getServiceSummary(it) })

    fun getServiceSummary(): LiveData<ServiceRequest> = serviceSummary
    fun getServiceRequirements(): LiveData<ApiResponse<ServiceRequirementResponse>> = serviceRequirements

    fun initializeInputMap(attributesResponse: List<ServiceRequestAttribute>) {
        attributesResponse.forEach { requestAttribute ->
            if (!requestAttribute.code.isNullOrEmpty()) {
                if (requestAttribute.required == true) {
                    requiredInputMap[requestAttribute.code!!] = emptyList()
                } else {
                    optionalInputMap[requestAttribute.code!!] = emptyList()
                }
            }
        }
    }

    fun onNewServiceCode(serviceCode: String) {
        if (this.serviceCode.value != serviceCode) {
            this.serviceCode.value = serviceCode
        }
    }

    fun onInputChange(code: String?, values: List<String>?) {
        code?.let {
            when {
                requiredInputMap.containsKey(code) -> {
                    Timber.d("Required input changed: $code -> $values")
                    requiredInputMap[it] = values
                }
                optionalInputMap.containsKey(code) -> {
                    Timber.d("Optional input changed: $code -> $values")
                    optionalInputMap[it] = values
                }
                else -> Timber.d("Code not found $code")
            }
        }
    }

    fun onSaveData(parentViewModel: NewRequestViewModel) {
        parentViewModel.updateAttributes(requiredInputMap, optionalInputMap)
    }

    fun onClearData(parentViewModel: NewRequestViewModel) {
        parentViewModel.clearAttributes()
    }

    fun validate(): Boolean {
        requiredInputMap.forEach {
            if (it.value == null || it.value!!.isEmpty()) {
                return false
            }
        }
        return true
    }
}