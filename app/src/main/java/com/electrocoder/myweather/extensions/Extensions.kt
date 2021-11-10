package com.electrocoder.myweather.extensions

import android.content.Context
import android.graphics.Color
import com.electrocoder.myweather.R
import com.electrocoder.myweather.constants.Constants
import java.security.AccessControlContext

/**
 * Extension function used to get background color corresponding to weather code (weather outside)
 *
 * @return color of the background
 */
fun Int.getWeatherColor(context: Context): Int = when {

    Constants.WEATHER_CLEAR == this -> context.getColor(R.color.orange)
    Constants.WEATHER_CLOUDS.contains(this) -> context.getColor(R.color.dark_blue)

    else -> context.getColor(R.color.dark_blue)

}