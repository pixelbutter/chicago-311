package com.chicago311.create

import android.content.Context
import android.support.annotation.IntRange
import android.support.v4.app.FragmentManager
import com.chicago311.R
import com.chicago311.create.contact.NewRequestContactFragment
import com.chicago311.create.details.NewRequestDetailsFragment
import com.chicago311.create.location.NewRequestLocationFragment
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel

class NewRequestStepperAdapter(private val serviceRequestCode: String, fm: FragmentManager, context: Context) :
        AbstractFragmentStepAdapter(fm, context) {

    override fun getViewModel(@IntRange(from = 0) position: Int): StepViewModel {
        // TODO
        val builder = StepViewModel.Builder(context)
        when (position) {
            0 -> builder.setTitle(context.getString(R.string.tab_title_details))
            1 -> builder.setTitle(R.string.tab_title_location_photos)
            2 -> {
                builder.setTitle(R.string.tab_title_contact)
                builder.setSubtitle(context.getString(R.string.optional))
            }
            else -> throw IllegalArgumentException("Unsupported position: " + position)
        }
        return builder.create()
    }

    override fun createStep(position: Int): Step {
        return when (position) {
            0 -> NewRequestDetailsFragment.createFragment(serviceRequestCode)
            1 -> NewRequestLocationFragment.createFragment()
            2 -> NewRequestContactFragment.createFragment()
            else -> throw IllegalArgumentException("Unsupported position: " + position)
        }
    }

    override fun getCount(): Int {
        return 3
    }
}