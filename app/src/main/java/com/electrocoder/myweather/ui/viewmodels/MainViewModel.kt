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
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext
import kotlin.math.log

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val currentWeatherForecast =
        savedStateHandle.getLiveData<String>("cityName").switchMap { query ->
            repository.getCurrentWeather(query).asLiveData()
        }

    val fiveDayForecast =
        savedStateHandle.getLiveData<String>("cityName").switchMap { query ->
            repository.getFiveDayForecast(query).asLiveData()
        }


    fun updateCityName(newCityName: String) {
        savedStateHandle["cityName"] = newCityName
    }

    fun getCityName(): String = savedStateHandle.get("cityName") ?: ""

}