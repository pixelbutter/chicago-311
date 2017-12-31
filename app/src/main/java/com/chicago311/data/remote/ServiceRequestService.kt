package com.chicago311.data.remote

import android.arch.lifecycle.LiveData
import com.chicago311.data.model.RequestDetails
import com.chicago311.data.model.RequestSummary
import com.chicago311.data.model.ServiceRequest
import com.chicago311.data.model.ServiceRequirementResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// rather unfortunate naming щ(ಠ益ಠщ)
interface ServiceRequestService {

    @GET("services.json")
    fun getAvailableServices(): LiveData<ApiResponse<List<ServiceRequest>>>

    @GET("services/{service_code}.json")
    fun getServiceDetails(@Path("service_code") code: String,
                          @Query("extensions") extensions: Boolean): LiveData<ApiResponse<ServiceRequirementResponse>>

    @GET("requests/{service_request_id}.json")
    fun getRequestDetails(@Path("service_request_id") requestId: String,
                          @Query("extensions") extensions: Boolean = true): LiveData<ApiResponse<List<RequestDetails>>>

    @GET("requests.json")
    fun getRecentRequests(@Query("status") status: String = "open,closed"): LiveData<ApiResponse<List<RequestSummary>>>
}