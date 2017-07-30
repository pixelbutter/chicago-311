package com.chicago311.api

import com.squareup.moshi.Json

data class ServiceRequest(
        @Json(name = "service_code") val code : String,
        @Json(name = "service_name") val name : String,
        val description : String,
        val group : String
)