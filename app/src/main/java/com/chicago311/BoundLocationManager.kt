package com.chicago311

import android.annotation.SuppressLint
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import timber.log.Timber

class BoundLocationManager {

    companion object {
        fun bindLocationListenerIn(lifecycleOwner: LifecycleRegistryOwner,
                                   listener: LocationListener,
                                   context: Context) {
            BoundLocationListener(context, listener, lifecycleOwner)
        }

        class BoundLocationListener(private val context: Context,
                                    private val listener: LocationListener,
                                    lifecycleOwner: LifecycleRegistryOwner) : LifecycleObserver {

            private var locationManager: LocationManager? = null

            init {
                lifecycleOwner.lifecycle.addObserver(this)
            }

            @SuppressLint("MissingPermission") // todo
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            internal fun addLocationListener() {
                locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)

            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            internal fun removeLocationListener() {
                locationManager?.apply {
                    this.removeUpdates(listener)
                    Timber.d("BoundLocationManager - listener removed")
                }
                locationManager = null
            }
        }
    }
}