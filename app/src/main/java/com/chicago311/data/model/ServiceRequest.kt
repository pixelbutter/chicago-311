package com.chicago311.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import com.squareup.moshi.Json

@Entity(tableName = "services",
        primaryKeys = arrayOf("service_code"))
data class ServiceRequest(
        @ColumnInfo(name = "service_code")
        @Json(name = "service_code")
        val code: String,
        @ColumnInfo(name = "service_name")
        @Json(name = "service_name")
        val name: String,
        val description: String,
        val group: String?
//        @ColumnInfo(name = "last_updated")
//        var lastUpdated: Date?
)