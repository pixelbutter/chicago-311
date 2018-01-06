package com.chicago311.create.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chicago311.R
import com.chicago311.data.model.CityService
import kotlinx.android.synthetic.main.item_service_request.view.*

class ServicesViewAdapter(private var cityServices: ArrayList<CityService>,
                          private val itemClick: (String) -> Unit) : RecyclerView.Adapter<ServicesViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service_request, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindService(cityServices[position])

    override fun getItemCount() = cityServices.size

    fun updateData(newData: List<CityService>) {
        cityServices.clear()
        cityServices.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View, val itemClick: (String) -> Unit) : RecyclerView.ViewHolder(view) {

        // todo handle null case
        fun bindService(cityService: CityService) {
            itemView.serviceName.text = cityService.name
            itemView.serviceCategory.text = cityService.group
            itemView.setOnClickListener { itemClick(cityService.code) }
        }
    }
}