package com.chicago311.create

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.chicago311.R
import com.chicago311.data.model.ServiceRequestAttribute
import kotlinx.android.synthetic.main.item_attribute_spinner.view.*

internal class AttributeSpinnerItemView : AttributeItemView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun update(attribute: ServiceRequestAttribute) {
        super.update(attribute)
        if (attribute.required == true) {
            attributeSpinnerPrompt.text = attribute.description
        } else {
            attributeSpinnerPrompt.text = context.getString(R.string.optional_input_field, attribute.description)
        }

        val options: List<String> = attribute.options?.map { option -> option.label } ?: emptyList()
        val optionsAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, options)
        optionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        attributeSpinner.adapter = optionsAdapter
        attributeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItem: View, position: Int, id: Long) {
                val selectedOption = attribute.options!![position]
                inputChangeListener?.onInputChanged(attribute.code!!, listOf(selectedOption.key))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // no op
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.item_attribute_spinner
    }
}