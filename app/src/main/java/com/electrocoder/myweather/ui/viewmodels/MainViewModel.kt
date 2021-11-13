package com.electrocoder.myweather.ui.viewmodels

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.*
import com.electrocoder.myweather.models.ApiResponse
import com.electrocoder.myweather.models.DayForecast
import com.electrocoder.myweather.models.Weather
import com.electrocoder.myweather.models.WeatherResponse
import com.electrocoder.myweather.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext
import kotlin.math.log

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var cityName: String = ""

    private val weatherForecastData = MutableLiveData<ApiResponse<WeatherResponse>>()
    private val fiveDayForecastData = MutableLiveData<ApiResponse<DayForecast>>()

    val weatherForecast: LiveData<ApiResponse<WeatherResponse>> get() = weatherForecastData
    val fiveDayForecast: LiveData<ApiResponse<DayForecast>> get() = fiveDayForecastData

    fun updateCityName(newCityName: String) {
        if(!TextUtils.equals(cityName, newCityName)) {

            cityName = newCityName

            weatherForecastData.value = ApiResponse.Loading()

            viewModelScope.launch {
                repository.getCurrentWeather(newCityName).collect {
                    weatherForecastData.value = it
                }

                repository.getFiveDayForecast(newCityName).collect {
                    fiveDayForecastData.value = it
                }
            }
        }
    }

}