package com.chicago311.data.model

import com.squareup.moshi.Json

data class RequestDetails(
        @Json(name = "service_request_id")
        val requestId: String,
        val status: String,
        @Json(name = "status_notes")
        val statusNotes: String?,
        @Json(name = "service_name")
        val serviceName: String,
        @Json(name = "description")
        val serviceDescription: String?,
        @Json(name = "agency_responsible")
        val agencyResponsible: String?,
        @Json(name = "service_notice")
        val serviceNotice: String?,
        val address: String?,
        val lat: Double?,
        val long: Double?,
        val mediaUrl: String?
)