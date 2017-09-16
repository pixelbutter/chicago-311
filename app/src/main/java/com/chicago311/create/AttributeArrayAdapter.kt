package com.chicago311.create

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.chicago311.data.model.ServiceRequestAttribute
import com.chicago311.data.model.ServiceRequestAttribute.InputViewType
import timber.log.Timber

internal class AttributeArrayAdapter(context: Context, attributes: List<ServiceRequestAttribute>)
    : ArrayAdapter<ServiceRequestAttribute>(context, 0, attributes), AttributeItemView.InputChangeListener  {

    private val attributes: MutableList<ServiceRequestAttribute> = mutableListOf()
    private val attributeInputMap: MutableMap<String, List<String>> = mutableMapOf()

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
        attributeView.setChangeListener(this)
        attributeView.update(attribute)
        return attributeView
    }

    override fun onInputChanged(code: String?, value: List<String>?) {
        if (code != null && value != null && value.isNotEmpty()) {
            Timber.d("Input changed: $code -> ${value[0]}")
            attributeInputMap[code] = value
        } else {
            Timber.d("Input changed to default value")
        }
    }
}