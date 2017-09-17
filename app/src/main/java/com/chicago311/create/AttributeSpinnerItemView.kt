package com.chicago311.create

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.chicago311.R
import com.chicago311.data.model.ServiceRequestAttribute
import com.chicago311.util.setTextWithAsteriskAfter
import kotlinx.android.synthetic.main.item_attribute_spinner.view.*

internal class AttributeSpinnerItemView : AttributeItemView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun update(attribute: ServiceRequestAttribute) {
        super.update(attribute)
        attribute.description?.let {
            if (attribute.required == true) {
                attributeSpinnerPrompt.setTextWithAsteriskAfter(it)
            } else {
                attributeSpinnerPrompt.text = it
            }
        }

        val options = mutableListOf<String>(context.getString(R.string.form_spinner_hint))
        if (attribute.options != null) {
            options.addAll(attribute.options.map { it.label })
        }

        val optionsAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, options)
        optionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        attributeSpinner.adapter = optionsAdapter
        attributeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItem: View, position: Int, id: Long) {
                if (position > 0) {
                    val selectedOption = attribute.options!![position - 1]
                    inputChangeListener?.onInputChanged(attribute.code, listOf(selectedOption.key))
                } else {
                    // todo
                    inputChangeListener?.onInputChanged(attribute.code, null)
                }
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