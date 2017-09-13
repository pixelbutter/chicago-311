package com.chicago311.create

import android.content.Context
import android.support.annotation.IntRange
import android.support.v4.app.FragmentManager
import com.chicago311.R
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel

class NewRequestStepperAdapter(fm: FragmentManager, context: Context) :
        AbstractFragmentStepAdapter(fm, context) {

    override fun getViewModel(@IntRange(from = 0) position: Int): StepViewModel {
        // TODO
        val builder = StepViewModel.Builder(context)
        when (position) {
            0 -> builder.setTitle(context.getString(R.string.tab_title_details))
            1 -> builder.setTitle(R.string.tab_title_location)
            2 -> {
                builder.setTitle(R.string.tab_title_contact)
                builder.setSubtitle(context.getString(R.string.optional))
            }
            else -> throw IllegalArgumentException("Unsupported position: " + position)
        }
        return builder.create()
    }

    override fun createStep(position: Int): Step {
        when (position) {
            0 -> return NewRequestDetailsFragment.createFragment()
            1 -> return NewRequestLocationFragment.createFragment()
            2 -> return NewRequestContactFragment.createFragment()
            else -> throw IllegalArgumentException("Unsupported position: " + position)
        }
    }

    override fun getCount(): Int {
        return 3
    }
}