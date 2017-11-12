package com.chicago311.create

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.chicago311.ChicagoApplication
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import javax.inject.Inject

abstract class BaseStepperFragment : Fragment(), BlockingStep {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var newRequestViewModel: NewRequestViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity.application as ChicagoApplication).getAppComponent().inject(this)
        newRequestViewModel = ViewModelProviders.of(activity).get(NewRequestViewModel::class.java)
    }

    protected abstract fun saveStepData(parentViewModel: NewRequestViewModel)
    protected abstract fun clearStepData(parentViewModel: NewRequestViewModel)

    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback?) {
        saveStepData(newRequestViewModel)
        callback?.goToNextStep()
    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback?) {
        saveStepData(newRequestViewModel)
        callback?.complete()
    }

    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback?) {
        clearStepData(newRequestViewModel)
        callback?.goToPrevStep()
    }

    override fun onError(error: VerificationError) = Unit

    override fun onSelected() = Unit
}