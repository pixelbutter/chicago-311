package com.chicago311.create.location

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.chicago311.create.NewRequestViewModel
import com.google.android.gms.location.places.Place
import javax.inject.Inject

class NewRequestLocationViewModel @Inject constructor() : ViewModel() {

    private var selectedPlace: MutableLiveData<Place?> = MutableLiveData()

    fun getObservableSelectedPlace(): LiveData<Place?> = selectedPlace

    fun onNewSelectedPlace(place: Place) {
        selectedPlace.value = place
    }

    fun onSaveData(parentViewModel: NewRequestViewModel) {
        selectedPlace.value?.let { place ->
            parentViewModel.updateLocation(place)
        }
    }

    fun onClearData(parentViewModel: NewRequestViewModel) {
        parentViewModel.clearLocation()
    }

    fun onValidate(): Boolean {
        return selectedPlace.value?.latLng != null
    }
}