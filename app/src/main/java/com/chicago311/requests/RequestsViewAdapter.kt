package com.chicago311.requests

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.R
import com.chicago311.data.model.RequestSummary
import kotlinx.android.synthetic.main.item_recent_request.view.*

class RequestsViewAdapter(private var requests: ArrayList<RequestSummary>,
                          private val itemClick: (String) -> Unit) : RecyclerView.Adapter<RequestsViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent_request, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(requests[position])

    override fun getItemCount() = requests.size

    fun updateData(newData: List<RequestSummary>) {
        requests.clear()
        requests.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View, val itemClick: (String) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bind(requestSummary: RequestSummary) {
            val requestId = requestSummary.requestId
            itemView.requestItemTitle.text = requestSummary.serviceName
            itemView.requestItemStatus.text = requestSummary.status
            itemView.requestItemId.text = requestId
            itemView.setOnClickListener { itemClick(requestId) }
        }
    }
}