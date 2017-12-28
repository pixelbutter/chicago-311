package com.chicago311.requests

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.R
import kotlinx.android.synthetic.main.fragment_requests.*

class RequestsLookupFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_requests, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_lookup_requests)
        // todo - validation
        findRequestSearchButton.setOnClickListener { startActivity(RequestDetailsActivity.createIntent(activity, findRequestInput.text.toString())) }
    }

    companion object {
        fun newInstance(): RequestsLookupFragment = RequestsLookupFragment()
    }
}