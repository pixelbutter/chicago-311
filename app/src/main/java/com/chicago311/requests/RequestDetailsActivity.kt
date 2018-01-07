package com.chicago311.requests

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.chicago311.ChicagoApplication
import com.chicago311.R
import com.chicago311.data.model.getStatusColor
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_request_details.*
import javax.inject.Inject

class RequestDetailsActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RequestDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.application as ChicagoApplication).getAppComponent().inject(this)
        setContentView(R.layout.activity_request_details)
        val requestId = savedInstanceState?.getString(EXTRA_REQUEST_ID) ?: intent.getStringExtra(EXTRA_REQUEST_ID)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RequestDetailsViewModel::class.java)
        subscribeToModel()
        viewModel.setRequestId(requestId)
        requestDetailsMap.onCreate(null)
    }

    private fun subscribeToModel() {
        // todo handle loading and error states
        viewModel.getDetails().observe(this, Observer { apiResponse ->
            if (apiResponse?.isSuccessful() == true && apiResponse.body?.isNotEmpty() == true) {
                val requestDetailsResponse = apiResponse.body[0]
                requestDetailsResponse.apply {
                    requestDetailsTitle.text = serviceName
                    requestDetailsStatus.text = status
                    requestDetailsNumber.text = getString(R.string.request_number, requestId)
                    requestDetailsStatus.setTextColor(ContextCompat.getColor(requestDetailsStatus.context, getStatusColor(status)))
                    requestDetailsAgency.text = getString(R.string.label_agency_responsible, agencyResponsible)

                    if (lat != null && long != null) {
                        requestDetailsAddress.text = address
                        requestDetailsMap.getMapAsync { map ->
                            val latLng = LatLng(lat, long)
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.5f))
                            map.addMarker(MarkerOptions().position(latLng))
                        }
                    }
                }
            }
        })
    }

    companion object {
        fun createIntent(context: Context, requestId: String): Intent {
            val intent = Intent(context, RequestDetailsActivity::class.java)
            intent.putExtra(EXTRA_REQUEST_ID, requestId)
            return intent
        }

        private const val EXTRA_REQUEST_ID = "extraRequestId"
    }
}