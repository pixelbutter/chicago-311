package com.chicago311.create.details

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import com.chicago311.R
import com.chicago311.data.model.ServiceRequestAttribute
import com.chicago311.data.model.ServiceRequestAttribute.InputViewType
import com.chicago311.util.setTextWithAsteriskAfter
import kotlinx.android.synthetic.main.item_attribute_text.view.*

internal class AttributeTextItemView : AttributeItemView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun update(attribute: ServiceRequestAttribute) {
        super.update(attribute)
        setupInputType(attribute)

        attribute.description?.let {
            if (attribute.required == true) {
                attributePrompt.setTextWithAsteriskAfter(it)
            } else {
                attributePrompt.text = it
            }
        }

        attributeInputField.hint = attribute.hint
        // todo rxbinding
        attributeInputField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                inputChangeListener?.onInputChanged(attribute.code!!, listOf(p0.toString()))
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // no-op
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // no-op
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.item_attribute_text
    }

    private fun setupInputType(attribute: ServiceRequestAttribute) {
        when (attribute.getInputViewType()) {
            InputViewType.PHONE_NUMBER -> attributeInputField.inputType = InputType.TYPE_CLASS_PHONE
            InputViewType.NUMBER -> attributeInputField.inputType = InputType.TYPE_CLASS_NUMBER
            InputViewType.DATETIME -> attributeInputField.inputType = InputType.TYPE_CLASS_DATETIME
            else -> attributeInputField.inputType = InputType.TYPE_CLASS_TEXT
        }
    }
}