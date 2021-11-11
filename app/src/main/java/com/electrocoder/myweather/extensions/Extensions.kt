package com.electrocoder.myweather.extensions

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import coil.load
import com.bumptech.glide.Glide
import com.electrocoder.myweather.R
import java.security.AccessControlContext
import com.electrocoder.myweather.constants.Constants

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

fun ImageView.loadDynamicWeatherImage(
    icon: String,
    imageSize: Constants.IMAGE_TYPE = Constants.IMAGE_TYPE.TYPE_LARGE) {
    when(imageSize) {

        Constants.IMAGE_TYPE.TYPE_LARGE -> {
            this.load(Constants.IMAGE_URL + icon + "@4x.png")
            /*Glide.with(this)
                .load(Constants.IMAGE_URL + icon + "@4x.png")
                .into(this)*/
            Log.d("TAG", Constants.IMAGE_URL + icon + "@4x.png")
        }
        Constants.IMAGE_TYPE.TYPE_SMALL -> {
            this.load(Constants.IMAGE_URL + icon + "@2x.png")
            Log.d("TAG", Constants.IMAGE_URL + icon + "@4x.png")
        }


    }
}