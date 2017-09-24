package com.chicago311.create.location

import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

internal class NewRequestLocationViewModel @Inject constructor() : ViewModel() {

    private var centerLocation = DEFAULT_LAT_LNG
    private var selectedLocation: LatLng? = null

    internal fun updateCenterLocation(latLng: LatLng?) {
        centerLocation = latLng ?: DEFAULT_LAT_LNG
    }

    internal fun getCameraCenterLocation(): LatLng {
        return selectedLocation ?: centerLocation
    }

    companion object {
        private val DEFAULT_LAT_LNG = LatLng(41.881832, -87.623177)
    }
}