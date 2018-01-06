package com.chicago311.data.model

import com.squareup.moshi.Json

data class NewCityRequestParams(
        @Json(name = "service_code")
        val serviceCode: String = "",
        val attributes: String = "", // todo
        val lat: Double? = null,
        val long: Double? = null,
        @Json(name = "address_string")
        val addressName: CharSequence? = null,
        val email: String? = null,
        @Json(name = "first_name")
        val firstName: String? = null,
        @Json(name = "last_name")
        val lastName: String? = null,
        val phone: String? = null,
        val description: String? = null,
        @Json(name = "media_url")
        val mediaUrl: String? = null)