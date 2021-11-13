package com.electrocoder.myweather.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.electrocoder.myweather.R
import com.electrocoder.myweather.constants.Constants
import com.electrocoder.myweather.databinding.WeatherItemBinding
import com.electrocoder.myweather.extensions.loadDynamicWeatherImage
import com.electrocoder.myweather.models.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*

class FiveDayRecyclerAdapter : ListAdapter<WeatherResponse, FiveDayRecyclerAdapter.ViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WeatherItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)

        holder.bindView(item)

    }

    class ViewHolder(val binding: WeatherItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun bindView(weatherResponse: WeatherResponse) {

                binding.weatherItemIcon.loadDynamicWeatherImage(
                    weatherResponse.weatherList[0].icon,
                    Constants.IMAGE_TYPE.TYPE_SMALL
                )

                binding.weatherItemDescription.text = weatherResponse.weatherList[0].main

                binding.weatherItemTemperature.text =
                    binding.weatherItemTemperature.context.getString(
                        R.string.temperature_text,
                        weatherResponse.mainWeather.temp
                    )

                Log.d("TAG", "bindView: VRIJEME UNIX: ${weatherResponse.time}")

                val date = Date(weatherResponse.time*1000L)
                val simpleDateFormat = SimpleDateFormat("dd/MM - k:mm", Locale.getDefault())

                binding.weatherItemDate.text = simpleDateFormat.format(date)

            }

    }


    private class DiffUtilCallback : DiffUtil.ItemCallback<WeatherResponse>() {

        override fun areItemsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean {
            return oldItem.cityName == newItem.cityName && oldItem.code == newItem.code &&
                    oldItem.mainWeather == newItem.mainWeather && oldItem.weatherList == newItem.weatherList
                    && oldItem.windInfo == newItem.windInfo
        }

        override fun areContentsTheSame(
            oldItem: WeatherResponse,
            newItem: WeatherResponse
        ): Boolean {
            return oldItem == newItem
        }

    }

}