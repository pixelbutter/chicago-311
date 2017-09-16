package com.chicago311.create

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.chicago311.data.model.ServiceRequestAttribute
import com.chicago311.data.model.ServiceRequestAttribute.InputViewType

internal class AttributeArrayAdapter(context: Context, attributes: List<ServiceRequestAttribute>,
                                     val inputChangeListener: AttributeItemView.InputChangeListener? = null)
    : ArrayAdapter<ServiceRequestAttribute>(context, 0, attributes) {

    private val attributes: MutableList<ServiceRequestAttribute> = mutableListOf()

    init {
        this.attributes.addAll(attributes)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val attribute: ServiceRequestAttribute = getItem(position)
        val attributeView: AttributeItemView

        if (convertView == null) {
            attributeView = when (attribute.getInputViewType()) {
                InputViewType.SPINNER -> AttributeSpinnerItemView(context)
                InputViewType.TEXT -> AttributeTextItemView(context)
                InputViewType.PHONE_NUMBER -> AttributeTextItemView(context)
                InputViewType.NUMBER -> AttributeTextItemView(context)
                InputViewType.DATETIME -> AttributeTextItemView(context)
                else -> AttributeTextItemView(context) // todo other types
            }
        } else {
            attributeView = convertView as AttributeItemView
        }
        attributeView.setChangeListener(inputChangeListener)
        attributeView.update(attribute)
        return attributeView
    }
}