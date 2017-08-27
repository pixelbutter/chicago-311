package com.chicago311.create

import android.content.Context
import android.util.AttributeSet
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
    }

    override fun getLayoutId(): Int {
        return R.layout.item_attribute_spinner
    }
}