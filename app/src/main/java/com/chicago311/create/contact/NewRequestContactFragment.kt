package com.chicago311.create.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.R
import com.chicago311.create.BaseStepperFragment

class NewRequestContactFragment : BaseStepperFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_request_contact_info, container, false)
    }

    companion object {
        fun createFragment(): NewRequestContactFragment {
            return NewRequestContactFragment()
        }
    }
}