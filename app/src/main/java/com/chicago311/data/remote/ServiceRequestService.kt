package com.chicago311.data.remote

import android.arch.lifecycle.LiveData
import com.chicago311.data.model.CityRequestDetails
import com.chicago311.data.model.CityRequestSummary
import com.chicago311.data.model.CityService
import com.chicago311.data.model.CityServiceRequirementResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// rather unfortunate naming щ(ಠ益ಠщ)
interface ServiceRequestService {

    @GET("services.json")
    fun getAvailableServices(): LiveData<ApiResponse<List<CityService>>>

    @GET("services/{service_code}.json")
    fun getServiceDetails(@Path("service_code") code: String,
                          @Query("extensions") extensions: Boolean): LiveData<ApiResponse<CityServiceRequirementResponse>>

    @GET("requests/{service_request_id}.json")
    fun getRequestDetails(@Path("service_request_id") requestId: String,
                          @Query("extensions") extensions: Boolean = true): LiveData<ApiResponse<List<CityRequestDetails>>>

    @GET("requests.json")
    fun getRecentRequests(@Query("status") status: String = "open,closed"): LiveData<ApiResponse<List<CityRequestSummary>>>
}