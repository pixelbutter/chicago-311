package com.chicago311.create

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.chicago311.data.model.ServiceRequestAttribute
import timber.log.Timber

internal class AttributeAdapter : RecyclerView.Adapter<AttributeAdapter.AttributeViewHolder>(),
        AttributeItemView.InputChangeListener {

    private val VIEW_TYPE_TEXT = 0
    private val VIEW_TYPE_SPINNER = 1
    private val VIEW_TYPE_NUMBER = 2
    private val VIEW_TYPE_DATETIME = 3
    private val VIEW_TYPE_CHECKBOXES = 4
    private val VIEW_TYPE_SIMPLE_MESSAGE = 5

    private val attributes: MutableList<ServiceRequestAttribute> = mutableListOf()
    private val attributeInputMap: MutableMap<String, List<String>> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributeViewHolder {
        val context = parent.context
        val itemView: AttributeItemView
        // todo different types
        when (viewType) {
            VIEW_TYPE_TEXT -> itemView = AttributeTextItemView(context)
            VIEW_TYPE_SPINNER -> itemView = AttributeSpinnerItemView(context)
            VIEW_TYPE_NUMBER -> itemView = AttributeTextItemView(context)
            VIEW_TYPE_DATETIME -> itemView = AttributeTextItemView(context)
            VIEW_TYPE_CHECKBOXES -> itemView = AttributeTextItemView(context)
            VIEW_TYPE_SIMPLE_MESSAGE -> itemView = AttributeTextItemView(context)
            else -> {
                itemView = AttributeTextItemView(context)
            }
        }
        itemView.setChangeListener(this)
        return AttributeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return attributes.size
    }

    override fun onBindViewHolder(holder: AttributeViewHolder, position: Int) {
        holder.bindAttribute(attributes[position])
    }

    override fun getItemViewType(position: Int): Int {
        var itemType = -1
        // todo change/move string literals outside of adapter
        val attribute = attributes[position]
        if (attribute.requiresInput == false) {
            itemType = VIEW_TYPE_SIMPLE_MESSAGE
        } else {
            when (attribute.inputType) {
                "string" -> itemType = VIEW_TYPE_TEXT
                "singlevaluelist" -> itemType = VIEW_TYPE_SPINNER
                "number" -> itemType = VIEW_TYPE_NUMBER
                "datetime" -> itemType = VIEW_TYPE_DATETIME
                "multivaluelist" -> itemType = VIEW_TYPE_CHECKBOXES
                else -> Timber.w("Unexpected inputType found: ${attribute.inputType}")
            }
        }
        return itemType
    }

    override fun onInputChanged(code: String, value: List<String>) {
        Timber.d("Input changed: $code -> ${value[0]}")
        attributeInputMap[code] = value
    }

    fun updateItems(newAttributes: List<ServiceRequestAttribute>) {
        attributes.clear()
        attributes.addAll(newAttributes)
        notifyDataSetChanged()
    }

    internal class AttributeViewHolder(itemView: AttributeItemView) : RecyclerView.ViewHolder(itemView) {
        fun bindAttribute(attribute: ServiceRequestAttribute) {
            (itemView as AttributeItemView).update(attribute)
        }
    }
}