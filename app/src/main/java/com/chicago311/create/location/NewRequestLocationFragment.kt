package com.chicago311.create.location

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v13.app.FragmentCompat
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.LOCATION_PERMISSION_REQUEST_CODE
import com.chicago311.R
import com.chicago311.create.BaseStepperFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_new_request_location.*

class NewRequestLocationFragment : BaseStepperFragment(), OnMapReadyCallback, FragmentCompat.OnRequestPermissionsResultCallback {

    private var map: GoogleMap? = null
    private var permissionGranted = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_request_location, container, false) as View
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mapViewBundle: Bundle? = null
        savedInstanceState?.let {
            mapViewBundle = it.getBundle(MAPVIEW_BUNDLE_KEY)
        }

        mapView.onCreate(mapViewBundle)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        permissionGranted = false
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true
                enableMyLocation()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(MAPVIEW_BUNDLE_KEY, outState.getBundle(MAPVIEW_BUNDLE_KEY) ?: Bundle())
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        val latLng = LatLng(41.881832, -87.623177)
        map.addMarker(MarkerOptions().position(latLng).title("Test"))
        updateCameraFocus(latLng)
    }

    override fun onSelected() {
        getLocationPermission()
        super.onSelected()
    }

    private fun getLocationPermission() {
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(context.applicationContext, locationPermission) == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true
            enableMyLocation()
        } else {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(locationPermission), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    private fun enableMyLocation() {
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(context.applicationContext, locationPermission) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission()
        } else if (map != null) {
            map!!.isMyLocationEnabled = true
        }
    }

    private fun updateCameraFocus(latLng: LatLng?) {
        val center = latLng ?: LatLng(41.881832, -87.623177)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 15f))
    }

    companion object {
        fun createFragment(): NewRequestLocationFragment {
            return NewRequestLocationFragment()
        }

        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }
}