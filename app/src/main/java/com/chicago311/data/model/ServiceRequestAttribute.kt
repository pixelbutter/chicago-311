package com.chicago311.data.model

import com.squareup.moshi.Json

data class ServiceRequestAttribute(
        val code: String? = null,
        @Json(name = "datatype")
        val inputType: String? = null,
        @Json(name = "datatype_description")
        val hint: String? = null,
        @Json(name = "values")
        val options: List<AttributeOption>? = null,
        @Json(name = "variable")
        val requiresInput: Boolean? = null,
        val required: Boolean? = null,
        val description: String? = null) {

    enum class InputViewType {
        TEXT, SPINNER, PHONE_NUMBER, NUMBER, DATETIME, CHECKBOXES, SIMPLE_MESSAGE, UNKNOWN
    }

    fun getInputViewType(): InputViewType {
        val type: InputViewType
        if (requiresInput == false) {
            type = InputViewType.SIMPLE_MESSAGE
        } else if (code?.equals(CODE_PHONE_NUMBER, true) == true) {
            type = InputViewType.PHONE_NUMBER
        } else when (inputType) {
            INPUT_TYPE_STRING -> type = InputViewType.TEXT
            INPUT_TYPE_SINGLE_VALUE_LIST -> type = InputViewType.SPINNER
            INPUT_TYPE_NUMBER -> type = InputViewType.NUMBER
            INPUT_TYPE_DATETIME -> type = InputViewType.DATETIME
            INPUT_TYPE_MULTIVALUE_LIST -> type = InputViewType.CHECKBOXES
            else -> type = InputViewType.UNKNOWN
        }
        return type
    }

    companion object {
        private const val CODE_PHONE_NUMBER = "A511OPTN"
        private const val INPUT_TYPE_STRING = "string"
        private const val INPUT_TYPE_SINGLE_VALUE_LIST = "singlevaluelist"
        private const val INPUT_TYPE_NUMBER = "number"
        private const val INPUT_TYPE_DATETIME = "datetime"
        private const val INPUT_TYPE_MULTIVALUE_LIST = "multivaluelist"
    }
}