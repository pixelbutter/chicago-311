package com.chicago311.data.model

import android.support.annotation.LayoutRes
import com.chicago311.R
import com.squareup.moshi.Json
import timber.log.Timber

data class ServiceRequestAttribute(
        @Json(name = "variable")
        val requiresInput: Boolean? = null,
        val code: String? = null,
        @Json(name = "datatype")
        val inputType: String? = null,
        val required: Boolean? = null,
        val description: String? = null) {

    @LayoutRes
    fun getLayoutResId(): Int {
        // todo support all types
        val layoutId: Int
        when (inputType) {
            "singlevaluelist" -> layoutId = R.layout.view_attribute_input_spinner
            "string" -> layoutId = R.layout.view_attribute_input_plaintext
            "number" -> layoutId = R.layout.view_attribute_input_number
            else -> {
                Timber.w("Unexpected inputType found: ${inputType}")
                layoutId = 0
            }
        }
        return layoutId
    }
}