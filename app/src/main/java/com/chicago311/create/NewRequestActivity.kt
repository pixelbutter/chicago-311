package com.chicago311.create

import android.arch.lifecycle.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chicago311.ChicagoApplication
import com.chicago311.EXTRA_SERVICE_CODE
import com.chicago311.R
import com.chicago311.data.model.ServiceRequirementResponse
import com.chicago311.data.remote.ApiResponse
import kotlinx.android.synthetic.main.activity_new_request.*
import javax.inject.Inject

class NewRequestActivity : AppCompatActivity(), LifecycleRegistryOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: NewRequestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.application as ChicagoApplication).getAppComponent().inject(this)
        setContentView(R.layout.activity_new_request)

        val serviceCode = savedInstanceState?.getString(EXTRA_SERVICE_CODE) ?:
                intent.getStringExtra(EXTRA_SERVICE_CODE)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewRequestViewModel::class.java)
        viewModel.updateCode(serviceCode)
        viewModel.getRequirements()
                .observe(this, Observer<ApiResponse<ServiceRequirementResponse>> {
                    if (it != null && it.isSuccessful() && it.body != null) {
                        val requirementResponse: ServiceRequirementResponse = it.body
                        attributesContent.text = ""
                        requirementResponse.attributes.forEach { attribute ->
                            attributesContent.append("${attribute.description} \n\n")
                        }
                    } else {
                        attributesContent.text = it?.errorMessage ?: "unknown error :("
                    }
                })

        viewModel.getServiceSummary()
                .observe(this, Observer {
                    if (it != null) {
                        title = it.name
                        description.text = it.description
                    }
                })
    }

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

    companion object {
        fun createIntent(context: Context, code: String): Intent {
            val intent = Intent(context, NewRequestActivity::class.java)
            intent.putExtra(EXTRA_SERVICE_CODE, code)
            return intent
        }
    }
}