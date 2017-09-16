package com.chicago311.create

import android.support.v4.app.Fragment
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError

abstract class BaseStepperFragment : Fragment(), Step {

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