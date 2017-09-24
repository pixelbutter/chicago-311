package com.chicago311.create

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.chicago311.ChicagoApplication
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import javax.inject.Inject

abstract class BaseStepperFragment : LifecycleFragment(), Step {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    protected lateinit var newRequestViewModel: NewRequestViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity.application as ChicagoApplication).getAppComponent().inject(this)
        newRequestViewModel = ViewModelProviders.of(activity).get(NewRequestViewModel::class.java)
    }

    override fun onSelected() {
        // TODO
    }

    override fun verifyStep(): VerificationError? {
        // TODO
        return null
    }

    override fun onError(error: VerificationError) {
        // TODO
    }
}