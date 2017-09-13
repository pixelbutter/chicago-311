package com.chicago311.create

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.R
import com.chicago311.data.model.ServiceRequirementResponse
import com.chicago311.data.remote.ApiResponse
import kotlinx.android.synthetic.main.fragment_new_request_details.*
import timber.log.Timber

class NewRequestDetailsFragment : BaseStepperFragment(), LifecycleRegistryOwner {

    private lateinit var viewModel: NewRequestViewModel
    private val lifecycleRegistry = LifecycleRegistry(this)
    private val attributeAdapter = AttributeAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_new_request_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newRequestAttributes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = attributeAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity).get(NewRequestViewModel::class.java)
        viewModel.serviceRequirements
                .observe(this, Observer<ApiResponse<ServiceRequirementResponse>> {
                    if (it != null && it.isSuccessful() && it.body != null) {
                        val requirementResponse: ServiceRequirementResponse = it.body
                        requirementResponse.attributes?.let { attributeAdapter.updateItems(it) }
                    } else {
                        Timber.w("Unknown error :(")
                    }
                })

        viewModel.serviceSummary
                .observe(this, Observer {
                    it?.let {
                        description.text = it.description
                    }
                })
    }

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

    companion object {
        fun createFragment(): NewRequestDetailsFragment {
            return NewRequestDetailsFragment()
        }
    }
}