package com.chicago311.data.model

import android.support.annotation.ColorRes
import com.chicago311.R
import com.squareup.moshi.Json

private const val STATUS_OPEN = "open"
private const val STATUS_CLOSED = "closed"

data class CityRequestSummary(@Json(name = "service_request_id")
                          val requestId: String,
                              val status: String,
                              @Json(name = "service_name")
                          val serviceName: String)

data class CityRequestDetails(
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

@ColorRes fun getStatusColor(status: String): Int {
    return when (status) {
        STATUS_OPEN -> R.color.requestStatusOpen
        STATUS_CLOSED -> R.color.requestStatusClosed
        else -> R.color.white
    }
}