package com.chicago311.data.model

import com.squareup.moshi.Json

data class RequestSummary(@Json(name = "service_request_id")
                          val requestId: String,
                          val status: String,
                          @Json(name = "service_name")
                          val serviceName: String)