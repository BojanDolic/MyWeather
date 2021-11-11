package com.electrocoder.myweather.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.electrocoder.myweather.models.ApiResponse
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

    private val cityName: MutableLiveData<String> = MutableLiveData("")

    private val weatherForecastData = MutableLiveData<ApiResponse<WeatherResponse>>()

    val weatherForecast: LiveData<ApiResponse<WeatherResponse>> get() = weatherForecastData


    val currentWeather = cityName.switchMap { cityName ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val result = repository.getCurrentWeather(cityName).asLiveData()
            emit(result)
            Log.d(TAG, ": UPDATE PODATAKA")
        }

    }


    fun updateCityName(newCityName: String) {
        weatherForecastData.value = ApiResponse.Loading()

        Log.d(TAG, "updateCityName: POZVANA FUNKCIJA PROMJENE IMENA")
        
        viewModelScope.launch {
            repository.getCurrentWeather(newCityName).collect {
                weatherForecastData.value = it
            }
        }
    }

}