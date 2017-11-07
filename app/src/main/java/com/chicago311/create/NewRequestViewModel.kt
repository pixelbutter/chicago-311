package com.chicago311.create

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.chicago311.data.model.ServiceRequestAttribute
import com.chicago311.repository.ServiceRequestRepository
import timber.log.Timber
import javax.inject.Inject

class NewRequestViewModel @Inject constructor(val repository: ServiceRequestRepository) : ViewModel() {

    private val requiredInputMap: MutableMap<String, List<String>?> = mutableMapOf()
    private val optionalInputMap: MutableMap<String, List<String>?> = mutableMapOf()
    private val serviceCode = MutableLiveData<String>()
    val serviceRequirements = Transformations.switchMap(serviceCode, {
        repository.getServiceRequirements(it)
    })
    val serviceSummary = Transformations.switchMap(serviceCode, {
        repository.getServiceSummary(it)
    })

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

    fun updateCode(serviceCode: String) {
        if (this.serviceCode.value != serviceCode) {
            this.serviceCode.value = serviceCode
        }
    }

    fun updateInput(code: String?, values: List<String>?) {
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

    fun validate(): Boolean {
        requiredInputMap.forEach {
            if (it.value == null || it.value!!.isEmpty()) {
                return false
            }
        }
        return true
    }
}