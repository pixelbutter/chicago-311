package com.chicago311.create.contact

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chicago311.R
import com.chicago311.create.BaseStepperFragment
import com.chicago311.create.NewRequestViewModel
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError

class NewRequestContactFragment : BaseStepperFragment() {

    private lateinit var viewModel: NewRequestContactViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_request_contact_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewRequestContactViewModel::class.java)
    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {
        super.onCompleteClicked(callback)
        // todo
        Toast.makeText(context.applicationContext, "Not implemented yet!", Toast.LENGTH_SHORT).show()
    }

    override fun saveStepData(parentViewModel: NewRequestViewModel) {
        // todo
    }

    override fun clearStepData(parentViewModel: NewRequestViewModel) {
        // todo
    }

    override fun verifyStep(): VerificationError? {
        // todo
        return null
    }

    companion object {
        fun createFragment(): NewRequestContactFragment {
            return NewRequestContactFragment()
        }
    }
}