package com.chicago311.create.location

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v13.app.FragmentCompat
import android.support.v4.content.PermissionChecker.PERMISSION_GRANTED
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chicago311.R
import com.chicago311.REQUEST_CODE_LOCATION_PERMISSION
import com.chicago311.create.BaseStepperFragment
import com.chicago311.create.NewRequestViewModel
import com.chicago311.util.isPermissionGranted
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.fragment_new_request_location.*

class NewRequestLocationFragment : BaseStepperFragment(), FragmentCompat.OnRequestPermissionsResultCallback {
    private lateinit var viewModel: NewRequestLocationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_request_location, container, false) as View
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseLocationButton.setOnClickListener {
            if (activity.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                goToPlacePicker()
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION_PERMISSION)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewRequestLocationViewModel::class.java)
        viewModel.getObservableSelectedPlace().observe(this, Observer { place ->
            handleSelectedPlace(place)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.onNewSelectedPlace(PlacePicker.getPlace(context, data))
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_LOCATION_PERMISSION -> {
                if (grantResults.isEmpty() || grantResults[0] != PERMISSION_GRANTED) {
                    // Still use place-picker which defaults to device location
                    Toast.makeText(context.applicationContext,
                            getString(R.string.location_permission_warning), Toast.LENGTH_SHORT).show()
                }
                goToPlacePicker()
            }
        }
    }

    override fun saveStepData(parentViewModel: NewRequestViewModel) {
        viewModel.onSaveData(parentViewModel)
    }

    override fun clearStepData(parentViewModel: NewRequestViewModel) {
        viewModel.onClearData(parentViewModel)
    }

    override fun verifyStep(): VerificationError? {
        // TODO("not implemented")
        return null
    }

    private fun goToPlacePicker() {
        startActivityForResult(PlacePicker.IntentBuilder().build(activity), PLACE_PICKER_REQUEST)
    }

    private fun handleSelectedPlace(place: Place?) {
        if (place == null) {
            locationText.text = "No location selected"
            chooseLocationButton.text = "Choose location"
        } else {
            locationText.text = "name: ${place.name}; address: ${place.address}; latLng: ${place.latLng}"
            chooseLocationButton.text = "Change location"
        }
    }

    companion object {
        fun createFragment(): NewRequestLocationFragment {
            return NewRequestLocationFragment()
        }

        private const val PLACE_PICKER_REQUEST = 1
    }
}