package com.electrocoder.myweather.models

import android.util.Log
import retrofit2.Response
import java.lang.Exception

abstract class ApiFetcher {

    suspend fun <T> fetchData(apiCall: suspend () -> Response<T>): ApiResponse<T> {
        try {
            Log.d("TAG", "fetchData: PREUZIMANJE PODATAKA....")
            val response = apiCall()
            if(response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ApiResponse.Success(body)
                }
            }
            return ApiResponse.Error(message = "${response.code()} || ${response.message()}")
        } catch (e: Exception) {
            return ApiResponse.Error(message = "Data fetching failed ${e.message}")
        }
    }

}