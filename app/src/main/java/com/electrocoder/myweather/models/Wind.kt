package com.electrocoder.myweather.models

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed") val windSpeed: Float = 0f,
    @SerializedName("deg") val windDegrees: Int = 0
)
