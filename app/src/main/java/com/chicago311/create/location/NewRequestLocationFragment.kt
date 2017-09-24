package com.chicago311.create.location

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.support.v13.app.FragmentCompat
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.BoundLocationManager
import com.chicago311.R
import com.chicago311.REQUEST_CODE_LOCATION_PERMISSION
import com.chicago311.create.BaseStepperFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_new_request_location.*
import timber.log.Timber

class NewRequestLocationFragment : BaseStepperFragment(), OnMapReadyCallback, FragmentCompat.OnRequestPermissionsResultCallback {

    private lateinit var locationViewModel: NewRequestLocationViewModel
    private var map: GoogleMap? = null
    private var permissionGranted = false
    private val gpsListener = NewRequestLocationListener()

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locationViewModel = ViewModelProviders.of(this, viewModelFactory).get(NewRequestLocationViewModel::class.java)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        permissionGranted = false
        when (requestCode) {
            REQUEST_CODE_LOCATION_PERMISSION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
        map.clear()
        val cameraCenter = locationViewModel.getCameraCenterLocation()
        map.addMarker(MarkerOptions().position(cameraCenter).title("Selected Location")) // todo use selectedLocation
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraCenter, 15f))
    }

    override fun onSelected() {
        getLocationPermission()
        super.onSelected()
    }

    private fun getLocationPermission() {
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(activity.applicationContext, locationPermission) == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true
            bindLocationListener()
            enableMyLocation()
        } else {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(locationPermission), REQUEST_CODE_LOCATION_PERMISSION)
        }
    }

    private fun bindLocationListener() {
        BoundLocationManager.bindLocationListenerIn(this, gpsListener, activity.applicationContext)
    }

    private fun enableMyLocation() {
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(context.applicationContext, locationPermission) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission()
        } else if (map != null) {
            map?.isMyLocationEnabled = true
        }
    }

    private fun updateLocation(latLng: LatLng?) {
        locationViewModel.updateCenterLocation(latLng)
        mapView.getMapAsync(this)
    }

    private inner class NewRequestLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            Timber.d("Location changed: ${location.latitude}, ${location.longitude}")
            this@NewRequestLocationFragment.updateLocation(LatLng(location.latitude, location.longitude))
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
            Timber.d("Provider enabled: $provider.")
        }

        override fun onProviderDisabled(provider: String?) {
        }

    }

    companion object {
        fun createFragment(): NewRequestLocationFragment {
            return NewRequestLocationFragment()
        }

        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }
}