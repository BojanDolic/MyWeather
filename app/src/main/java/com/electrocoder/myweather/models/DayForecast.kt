package com.electrocoder.myweather.models

import com.google.gson.annotations.SerializedName

data class DayForecast(
    @SerializedName("list") val weatherList: List<WeatherResponse> = listOf()
)