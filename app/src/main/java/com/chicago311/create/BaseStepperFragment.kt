package com.chicago311.create

import android.support.v4.app.Fragment
import android.widget.Toast
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError

abstract class BaseStepperFragment : Fragment(), Step {



    override fun onSelected() {
        // TODO
        Toast.makeText(context, "Stepper fragment: onSelected()", Toast.LENGTH_SHORT).show()
    }

    override fun verifyStep(): VerificationError? {
        // TODO
        Toast.makeText(context, "Stepper fragment: verifyStep()", Toast.LENGTH_SHORT).show()
        return null
    }

    override fun onError(error: VerificationError) {
        Toast.makeText(context, "Stepper fragment: onError()", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun createFragment(): NewRequestDetailsFragment {
            return NewRequestDetailsFragment()
        }
    }
}