package com.chicago311.data.model

import com.squareup.moshi.Json

data class ServiceRequestAttribute(
        val code: String? = null,
        @Json(name = "datatype")
        val inputType: String? = null,
        @Json(name = "datatype_description")
        val hint: String? = null,
        @Json(name = "variable")
        val requiresInput: Boolean? = null,
        val required: Boolean? = null,
        val description: String? = null)