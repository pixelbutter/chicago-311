package com.chicago311.create.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.ChicagoApplication
import com.chicago311.R
import com.chicago311.create.NewRequestActivity
import com.chicago311.data.Status
import kotlinx.android.synthetic.main.fragment_new_request_list.*
import javax.inject.Inject

class NewRequestListFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ServiceListViewModel
    private var listAdapter = ServicesViewAdapter(ArrayList()) {
        startActivity(NewRequestActivity.createIntent(activity, it))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_request_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity.application as ChicagoApplication).getAppComponent().inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ServiceListViewModel::class.java)

        setupViews()

        // Handle changes emitted by LiveData
        viewModel.getServices().observe(this, Observer { resource ->
            // TODO - enhance error scenarios - spinner, retry, etc
            when (resource?.status) {
                Status.LOADING -> {
                    availableServicesText.visibility = View.VISIBLE
                    servicesRecyclerView.visibility = View.GONE
                    availableServicesText.text = getString(R.string.loading_with_ellipsis)
                }
                Status.ERROR -> {
                    availableServicesText.visibility = View.VISIBLE
                    servicesRecyclerView.visibility = View.GONE
                    availableServicesText.text = if (resource.message.isNullOrBlank() == true) {
                        getString(R.string.error_message_request_list_failed)
                    } else {
                        resource.message
                    }
                }
                Status.SUCCESS -> {
                    val data = resource.data
                    if (data?.isNotEmpty() == true) {
                        availableServicesText.visibility = View.GONE
                        servicesRecyclerView.visibility = View.VISIBLE
                        listAdapter.updateData(resource.data)
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
