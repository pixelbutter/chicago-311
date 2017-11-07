package com.chicago311.create.location

import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

@JvmField
val DEFAULT_LAT_LNG = LatLng(41.881832, -87.623177)

class NewRequestLocationViewModel @Inject constructor() : ViewModel() {

    private var centerLocation = DEFAULT_LAT_LNG
    private var selectedLocation: LatLng? = null

    fun updateCenterLocation(latLng: LatLng?) {
        centerLocation = latLng ?: DEFAULT_LAT_LNG
    }

    fun getCameraCenterLocation(): LatLng {
        return selectedLocation ?: centerLocation
    }
}