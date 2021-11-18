package com.electrocoder.myweather.extensions

import android.util.Log
import android.widget.ImageView
import coil.load
import com.electrocoder.myweather.constants.Constants


/**
 * Extension function used to load different image sizes based on imageSize parameter
 * using Coil library
 *
 * @param icon icon name retrieved from api
 * @param imageSize size of the image to be loaded
 *
 */
fun ImageView.loadDynamicWeatherImage(
    icon: String,
    imageSize: Constants.IMAGE_TYPE = Constants.IMAGE_TYPE.TYPE_LARGE) {
    when(imageSize) {

        Constants.IMAGE_TYPE.TYPE_LARGE -> {
            this.load(Constants.IMAGE_URL + icon + "@4x.png")
        }
        Constants.IMAGE_TYPE.TYPE_SMALL -> {
            this.load(Constants.IMAGE_URL + icon + "@2x.png")
        }


    }
}