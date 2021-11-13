package com.electrocoder.myweather.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.electrocoder.myweather.R
import com.electrocoder.myweather.constants.Constants
import com.electrocoder.myweather.databinding.MainFragmentBinding
import com.electrocoder.myweather.extensions.loadDynamicWeatherImage
import com.electrocoder.myweather.models.ApiResponse
import com.electrocoder.myweather.ui.adapters.FiveDayRecyclerAdapter
import com.electrocoder.myweather.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainFragment"

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = FiveDayRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Starting state of view
         */
        binding.errorContainer.isVisible = false
        binding.weatherLoadingIndicator.isVisible = false
        binding.cityWeatherCard.isVisible = false
        binding.weatherInfoContainer.isVisible = false


        //WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.searchContainer) { _view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            _view.updateLayoutParams<ViewGroup.MarginLayoutParams>() {
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }

        binding.citySearchEdLayout.setEndIconOnClickListener {

            val cityName = binding.citySearchEd.text.toString()
            viewModel.updateCityName(cityName)
        }



        viewModel.weatherForecast.observe(viewLifecycleOwner) { response ->

            when (response) {

                is ApiResponse.Success -> {

                    binding.errorContainer.isVisible = false
                    binding.weatherLoadingIndicator.isVisible = false
                    binding.cityWeatherCard.isVisible = true
                    binding.weatherInfoContainer.isVisible = true

                    val weatherResponse = response.data
                    weatherResponse?.let { weather ->

                        val weatherInfo = weather.weatherList[0]

                        binding.weatherTemp.text = getString(
                            R.string.temperature_text,
                            weather.mainWeather.temp
                        )

                        binding.cityName.text = weather.cityName
                        binding.weatherInfo.text = weatherInfo.main
                        binding.weatherDescription.text = weatherInfo.description

                        binding.weatherWindspeed.text = getString(
                            R.string.wind_speed_text,
                            weather.windInfo.windSpeed
                        )

                        binding.weatherPressure.text = getString(
                            R.string.atmospheric_pressure_text,
                            weather.mainWeather.pressure
                        )

                        binding.weatherHumidity.text = getString(
                            R.string.humidity_text,
                            weather.mainWeather.humidity
                        )

                        binding.weatherIcon.loadDynamicWeatherImage(
                            weatherInfo.icon,
                            Constants.IMAGE_TYPE.TYPE_LARGE
                        )


                    }


                }

                is ApiResponse.Loading -> {
                    binding.errorContainer.isVisible = false
                    binding.cityWeatherCard.isVisible = true
                    binding.weatherInfoContainer.isVisible = false
                    binding.weatherLoadingIndicator.isVisible = true
                }

                is ApiResponse.Error -> {

                    binding.weatherLoadingIndicator.isVisible = false
                    binding.errorContainer.isVisible = true
                    binding.errorDescription.text = response.message

                }

            }
        }

        viewModel.fiveDayForecast.observe(viewLifecycleOwner) { response ->

            when (response) {

                is ApiResponse.Success -> {

                    response.data?.let { fiveDayForecast ->
                        adapter.submitList(fiveDayForecast.weatherList)
                        binding.recyclerview.adapter = adapter
                    }

                }
                
                is ApiResponse.Error -> {
                    Log.d(TAG, "onViewCreated: ${response.message}")
                }

            }

        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}