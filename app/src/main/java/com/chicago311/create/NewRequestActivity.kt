package com.chicago311.create

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chicago311.ChicagoApplication
import com.chicago311.EXTRA_SERVICE_REQUEST_ID
import com.chicago311.R
import com.chicago311.data.model.ServiceDefinitionResponse
import com.chicago311.data.remote.ApiResponse
import kotlinx.android.synthetic.main.activity_new_request.*
import javax.inject.Inject

class NewRequestActivity : LifecycleActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: NewRequestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_request)

        val serviceRequestId = intent.getStringExtra(EXTRA_SERVICE_REQUEST_ID)
        (this.application as ChicagoApplication).getAppComponent().inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewRequestViewModel::class.java)
        viewModel.getServiceDefinition(serviceRequestId)
                .observe(this, Observer<ApiResponse<ServiceDefinitionResponse>> {
                    if (it != null && it.isSucessful() && it.body != null) {
                        val definitionResponse: ServiceDefinitionResponse = it.body
                        content.text = "Response:\n\n"
                        definitionResponse.attributes.forEach { attribute ->
                            content.append("${attribute.description} \n\n")
                        }
                    } else {
                        content.text = it?.errorMessage ?: "unknown error :("
                    }
                })
    }

    companion object {
        fun createIntent(context: Context, code: String): Intent {
            val intent = Intent(context, NewRequestActivity::class.java)
            intent.putExtra(EXTRA_SERVICE_REQUEST_ID, code)
            return intent
        }
    }
}