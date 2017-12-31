package com.chicago311.requests

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.ChicagoApplication
import com.chicago311.R
import kotlinx.android.synthetic.main.fragment_requests.*
import javax.inject.Inject


class RequestsLookupFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RequestsLookupViewModel
    private var listAdapter = RequestsViewAdapter(ArrayList(), { goToRequestDetails(it) })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_requests, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity.application as ChicagoApplication).getAppComponent().inject(this)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_lookup_requests)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RequestsLookupViewModel::class.java)
        setupViews()

        // todo use resource
        viewModel.getRecentRequests().observe(this, Observer { response ->
            if (response?.isSuccessful() == true && response.body != null) {
                listAdapter.updateData(response.body)
            } else {
                // todo whomp whomp
            }
        })
    }

    private fun setupViews() {
        val layoutManager = LinearLayoutManager(context)
        requestsRecyclerView.layoutManager = layoutManager
        requestsRecyclerView.adapter = listAdapter
        val dividerItemDecoration = DividerItemDecoration(requestsRecyclerView.context, layoutManager.orientation)
        requestsRecyclerView.addItemDecoration(dividerItemDecoration)

        // todo - validation
        findRequestSearchButton.setOnClickListener { goToRequestDetails(findRequestInput.text.toString()) }
    }

    private fun goToRequestDetails(requestId: String) {
        startActivity(RequestDetailsActivity.createIntent(activity, requestId))
    }

    companion object {
        fun newInstance(): RequestsLookupFragment = RequestsLookupFragment()
    }
}