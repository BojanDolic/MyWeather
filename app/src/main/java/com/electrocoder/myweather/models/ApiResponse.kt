package com.electrocoder.myweather.models

sealed class ApiResponse<T>(
    val message: String = "",
    val data: T? = null
) {

    class Success<T>(data: T) : ApiResponse<T>(data = data)

    class Error<T>(data: T? = null, message: String) : ApiResponse<T>(message, data)

    class Loading<T> : ApiResponse<T>()

}