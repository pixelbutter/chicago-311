package com.chicago311.requests

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chicago311.ChicagoApplication
import com.chicago311.R
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
    }

    private fun subscribeToModel() {
        viewModel.getDetails().observe(this, Observer { apiResponse ->
            if (apiResponse?.isSuccessful() == true) {
                requestDetailsText.text = apiResponse.body?.get(0)?.toString() ?: "response is successful!"
            } else {
                requestDetailsText.text = apiResponse?.errorMessage
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