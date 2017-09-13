package com.chicago311.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.R

class NewRequestLocationFragment : BaseStepperFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_request_location, container, false)
    }

    companion object {
        fun createFragment(): NewRequestLocationFragment {
            return NewRequestLocationFragment()
        }
    }
}