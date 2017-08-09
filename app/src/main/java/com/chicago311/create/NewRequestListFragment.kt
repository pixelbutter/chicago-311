package com.chicago311.create

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.ChicagoApplication
import com.chicago311.R
import com.chicago311.data.Resource
import com.chicago311.data.Status
import com.chicago311.data.model.ServiceRequest
import kotlinx.android.synthetic.main.fragment_new_request_list.*
import javax.inject.Inject

class NewRequestListFragment : LifecycleFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ServiceListViewModel
    private var listAdapter = ServicesViewAdapter(ArrayList<ServiceRequest>()) {
        startActivity(NewRequestActivity.createIntent(activity, it))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_new_request_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity.application as ChicagoApplication).getAppComponent().inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ServiceListViewModel::class.java)

        setupViews()

        // Handle changes emitted by LiveData
        viewModel.getServices().observe(this, Observer<Resource<List<ServiceRequest>>> {
            // TODO - enhance error scenarios - spinner, retry, etc
            when (it?.status) {
                Status.LOADING -> {
                    availableServicesText.visibility = View.VISIBLE
                    servicesRecyclerView.visibility = View.GONE
                    availableServicesText.text = getString(R.string.loading_with_ellipsis) // TODO spinner
                }
                Status.ERROR -> {
                    availableServicesText.visibility = View.VISIBLE
                    servicesRecyclerView.visibility = View.GONE
                    availableServicesText.text = if (TextUtils.isEmpty(it.message)) {
                        getString(R.string.error_message_request_list_failed)
                    } else {
                        it.message
                    }
                }
                Status.SUCCESS -> {
                    val data = it.data
                    if (data?.isNotEmpty() == true) {
                        availableServicesText.visibility = View.GONE
                        servicesRecyclerView.visibility = View.VISIBLE
                        listAdapter.updateData(it.data)
                    } else {
                        availableServicesText.visibility = View.VISIBLE
                        servicesRecyclerView.visibility = View.GONE
                        availableServicesText.text = getString(R.string.error_message_request_list_failed)
                    }
                }
            }
        })
    }

    private fun setupViews() {
        val layoutManager = LinearLayoutManager(context)
        servicesRecyclerView.layoutManager = layoutManager
        servicesRecyclerView.adapter = listAdapter
        val dividerItemDecoration = DividerItemDecoration(servicesRecyclerView.context, layoutManager.orientation)
        servicesRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    companion object {
        fun newInstance(): NewRequestListFragment {
            return NewRequestListFragment()
        }
    }
}
