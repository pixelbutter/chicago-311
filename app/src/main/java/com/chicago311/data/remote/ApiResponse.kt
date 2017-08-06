package com.chicago311.data.remote

import retrofit2.Response
import timber.log.Timber
import java.io.IOException

// Simple wrapper around Retrofit2.Call class to convert its response into a LiveData.
class ApiResponse<T> {

    val code: Int
    val body: T?
    val errorMessage: String?

    constructor(error: Throwable) {
        this.code = 500
        this.body = null
        this.errorMessage = error.message
    }

    constructor(response: Response<T>) {
        this.code = response.code()
        if (response.isSuccessful) {
            this.body = response.body()
            this.errorMessage = null
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody()?.string()
                } catch (ignored: IOException) {
                    Timber.e(ignored, "Error while parsing response")
                }
            }
            if (message == null || message.isEmpty()) {
                message = response.message()
            }
            this.errorMessage = message
            this.body = null
        }
    }

    fun isSucessful() = code in 200..299
}