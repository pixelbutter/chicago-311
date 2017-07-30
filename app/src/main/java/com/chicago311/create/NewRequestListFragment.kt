package com.chicago311.create

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.R
import com.chicago311.api.ServiceRequest
import kotlinx.android.synthetic.main.fragment_create_request.*

class NewRequestListFragment : LifecycleFragment() {

    private lateinit var viewModel: ServiceListViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_create_request, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_new_request)

        viewModel = ViewModelProviders.of(this).get(ServiceListViewModel::class.java)
        // TODO load from db
        viewModel.loadAvailableServices();

        // Handle changes emitted by LiveData
        viewModel.getApiResponse().observe(this, Observer<List<ServiceRequest>> {
            if (it?.isNotEmpty() == true) {
                it.forEach {
                    availableServicesText.append("${it.name}\n${it.description}\n\n")
                }
            } else {
                availableServicesText.setText("Boo error :(")
            }
        })
    }

    companion object {
        fun newInstance(): NewRequestListFragment {
            return NewRequestListFragment()
        }
    }
}
