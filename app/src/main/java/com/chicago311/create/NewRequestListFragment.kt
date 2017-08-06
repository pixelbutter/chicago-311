package com.chicago311.create

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.ChicagoApplication
import com.chicago311.R
import com.chicago311.data.Resource
import com.chicago311.data.Status
import com.chicago311.data.model.ServiceRequest
import kotlinx.android.synthetic.main.fragment_create_request.*
import javax.inject.Inject

class NewRequestListFragment : LifecycleFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ServiceListViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_create_request, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //(activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_new_request)

        (activity.application as ChicagoApplication).getAppComponent().inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ServiceListViewModel::class.java)

        // Handle changes emitted by LiveData
        viewModel.getServices().observe(this, Observer<Resource<List<ServiceRequest>>> {
            // TODO - enhance error scenarios - spinner, retry, etc
            when (it?.status) {
                Status.LOADING -> {
                    availableServicesText.text = getString(R.string.loading_with_ellipsis) // TODO spinner
                }
                Status.ERROR -> {
                    availableServicesText.text = if (TextUtils.isEmpty(it.message)) {
                        getString(R.string.error_message_request_list_failed)
                    } else {
                        it.message
                    }
                }
                Status.SUCCESS -> {
                    val data = it.data
                    if (data?.isNotEmpty() == true) {
                        availableServicesText.text = ""
                        data.forEach {
                            availableServicesText.append("${it.name}\n${it.description}\n\n")
                        }
                    } else {
                        availableServicesText.text = getString(R.string.error_message_request_list_failed)
                    }
                }
            }
        })
    }

    companion object {
        fun newInstance(): NewRequestListFragment {
            return NewRequestListFragment()
        }
    }
}
