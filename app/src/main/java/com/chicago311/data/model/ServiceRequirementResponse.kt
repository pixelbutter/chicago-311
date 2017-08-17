package com.chicago311.data.model

import com.squareup.moshi.Json

data class ServiceRequirementResponse(
        @Json(name = "service_code")
        val code: String,
        @Json(name = "attributes")
        val attributes:List<ServiceRequestAttribute>
)