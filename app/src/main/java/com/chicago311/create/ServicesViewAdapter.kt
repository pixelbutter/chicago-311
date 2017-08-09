package com.chicago311.create

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.R
import com.chicago311.data.model.ServiceRequest
import kotlinx.android.synthetic.main.item_service_request.view.*

class ServicesViewAdapter(var services: ArrayList<ServiceRequest>, val itemClick: (String) -> Unit) :
        RecyclerView.Adapter<ServicesViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service_request, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindService(services[position])

    override fun getItemCount() = services.size

    fun updateData(newData: List<ServiceRequest>) {
        services.clear()
        services.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View, val itemClick: (String) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindService(serviceRequest: ServiceRequest) {
            itemView.serviceName.text = serviceRequest.name
            itemView.serviceCategory.text = serviceRequest.group
            itemView.setOnClickListener { itemClick(serviceRequest.code) }
        }
    }
}