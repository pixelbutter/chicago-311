package com.chicago311.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// rather unfortunate naming щ(ಠ益ಠщ)
interface ServiceRequestService {

    // Todo livedata?
    @GET("services.json")
    fun getAvailableServices(): Observable<List<ServiceRequest>>

    @GET("services/{service_code}.json")
    fun getServiceDetails(@Path("service_code") code: String,
                          @Query("extensions") extensions: Boolean): Observable<ServiceDetailsResponse>
}