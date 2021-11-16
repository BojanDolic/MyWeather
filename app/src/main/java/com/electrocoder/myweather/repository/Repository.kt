package com.electrocoder.myweather.repository

import com.electrocoder.myweather.BuildConfig
import com.electrocoder.myweather.api.WeatherApi
import com.electrocoder.myweather.models.ApiFetcher
import com.electrocoder.myweather.models.ApiResponse
import com.electrocoder.myweather.models.DayForecast
import com.electrocoder.myweather.models.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    val api: WeatherApi
) : ApiFetcher() {


    /**
     * Function used to retrieve data about current weather in given city
     * @param city the city for which the weather data will be retrieved
     *
     * @return returns flow of API response
     */
    suspend fun getCurrentWeather(city: String): Flow<ApiResponse<WeatherResponse>> {
        return flow {
            emit(fetchData {
                api.getCurrentWeather(
                    city,
                    apiKey = BuildConfig.WEATHER_KEY
                )
            })
        }
    }

    /**
     * Function used to retrieve data about 5 day / 3 hour weather in given city
     * @param city the city for which the weather data will be retrieved
     *
     * @return returns flow of API response
     */
    suspend fun getFiveDayForecast(city: String): Flow<ApiResponse<DayForecast>> {
        return flow {
            emit(fetchData { api.getFiveDayForecast(
                city,
                apiKey = BuildConfig.WEATHER_KEY
            ) })
        }
    }


}