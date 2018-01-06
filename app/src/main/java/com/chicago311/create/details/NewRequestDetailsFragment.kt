package com.chicago311.create.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.R
import com.chicago311.create.BaseStepperFragment
import com.chicago311.create.NewRequestViewModel
import com.chicago311.data.model.CityServiceRequirementResponse
import com.chicago311.data.remote.ApiResponse
import com.chicago311.util.setTextWithAsteriskBefore
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.fragment_new_request_details.*
import timber.log.Timber

class NewRequestDetailsFragment : BaseStepperFragment(), AttributeItemView.InputChangeListener {

    private lateinit var viewModel: NewRequestDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_new_request_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(NewRequestDetailsViewModel::class.java)
        subscribeToModel()
        viewModel.onNewServiceCode(arguments.getString(ARG_SERVICE_REQUEST_CODE))
    }

    override fun onInputChanged(code: String?, values: List<String>?) {
        viewModel.onInputChange(code, values)
    }

    override fun verifyStep(): VerificationError? {
        return if (viewModel.validate()) null else VerificationError(getString(R.string.form_validation_missing_fields))
    }

    override fun saveStepData(parentViewModel: NewRequestViewModel) {
        viewModel.onSaveData(parentViewModel)
    }

    override fun clearStepData(parentViewModel: NewRequestViewModel) {
        viewModel.onClearData(parentViewModel)
    }

    private fun subscribeToModel() {
        viewModel.getServiceSummary()
                .observe(this, Observer { serviceRequest ->
                    serviceRequest?.let { request ->
                        activity.actionBar?.subtitle = request.name
                        description.text = request.description
                    }
                })

        viewModel.getServiceRequirements()
                .observe(this, Observer<ApiResponse<CityServiceRequirementResponse>> {
                    if (it != null && it.isSuccessful() && it.body != null) {
                        val requirementResponse: CityServiceRequirementResponse = it.body
                        requirementResponse.attributes?.let {
                            viewModel.initializeInputMap(it)
                            val adapter = AttributeArrayAdapter(context, it, this)
                            for (i in 0 until adapter.count) {
                                attributeContainer.addView(adapter.getView(i, null, null))
                            }
                            // TODO only show if there are required attributes
                            requiredFieldDescription.setTextWithAsteriskBefore(getString(R.string.form_required_footnote_suffix))
                        }
                    } else {
                        Timber.w("Unknown error :(")
                        // TODO: handle unhappy
                    }
                })
    }

    companion object {
        fun createFragment(code: String): NewRequestDetailsFragment {
            val fragment = NewRequestDetailsFragment()
            val bundle = Bundle()
            bundle.putString(ARG_SERVICE_REQUEST_CODE, code)
            fragment.arguments = bundle
            return fragment
        }

        private const val ARG_SERVICE_REQUEST_CODE = "argServiceRequestCode"
    }
}