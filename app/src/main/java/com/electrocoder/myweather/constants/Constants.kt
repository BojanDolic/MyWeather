package com.electrocoder.myweather.constants

object Constants {

    /**
     * Constants of weather codes returned by API
     * Every range goes from x00 to x99 because more codes could be added if future so we
     * make room for more codes.
     */
    const val WEATHER_CLEAR = 800
    val WEATHER_CLOUDS = 801..804
    val WEATHER_ATMOSPHERE = 700..799
    val WEATHER_SNOW = 600.699
    val WEATHER_RAIN = 500.599
    val WEATHER_DRIZZLE = 300.399
    val WEATHER_THUNDERSTORM = 200.299

}