package com.electrocoder.myweather.api

import com.electrocoder.myweather.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String): Response<WeatherResponse>


}