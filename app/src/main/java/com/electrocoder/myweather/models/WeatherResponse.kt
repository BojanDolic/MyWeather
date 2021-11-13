package com.electrocoder.myweather.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("cod") val code: Int,
    @SerializedName("dt") val time: Long = 0,
    @SerializedName("main") val mainWeather: WeatherMain,
    @SerializedName("wind") val windInfo: Wind,
    @SerializedName("name") val cityName: String = "",
    @SerializedName("weather") val weatherList: List<Weather> = listOf()
)
