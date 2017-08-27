package com.chicago311.data.model

import com.squareup.moshi.Json

data class AttributeOption(
        val key: String,
        @Json(name = "name")
        val label: String)