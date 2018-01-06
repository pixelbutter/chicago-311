package com.chicago311.create

import android.arch.lifecycle.ViewModel
import com.chicago311.data.model.NewCityRequestParams
import com.google.android.gms.location.places.Place
import javax.inject.Inject

class NewRequestViewModel @Inject constructor() : ViewModel() {

    private var requestParams = NewCityRequestParams()

    fun updateServiceCode(serviceCode: String) {
        if (requestParams.serviceCode != serviceCode) {
            requestParams = requestParams.copy(serviceCode = serviceCode)
        }
    }

    fun updateAttributes(requiredInputMap: MutableMap<String, List<String>?>,
                         optionalInputMap: MutableMap<String, List<String>?>) {
        // todo
        requestParams = requestParams.copy(attributes = requiredInputMap.toString())
    }

    fun clearAttributes() {
        requestParams = requestParams.copy(attributes = "")
    }

    fun updateLocation(place: Place) {
        val latLng = place.latLng
        requestParams = requestParams.copy(lat = latLng.latitude, long = latLng.longitude, addressName = place.address)
    }

    fun clearLocation() {
        requestParams = requestParams.copy(lat = null, long = null, addressName = null)
    }
}