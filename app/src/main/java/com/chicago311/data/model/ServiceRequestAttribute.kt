package com.chicago311.data.model

import com.squareup.moshi.Json

data class ServiceRequestAttribute(
        @Json(name = "variable")
        val requiresInput: Boolean,
        val code: String,
        @Json(name = "datatype")
        val inputType: String,
        val required: Boolean,
        val description: String
)