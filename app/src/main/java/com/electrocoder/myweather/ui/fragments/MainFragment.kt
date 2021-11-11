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
import com.electrocoder.myweather.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "MainFragment"

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

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

            when(response) {

                is ApiResponse.Success -> {


                    binding.cityWeatherCard.isVisible = true

                    Log.d(TAG, "onViewCreated: USPJEŠNO PREUZIMANJE")

                    val weatherResponse = response.data
                    weatherResponse?.let { weather ->

                        val weatherInfo = weather.weatherList[0]

                        binding.weatherTemp.text = getString(R.string.temperature_text, weather.mainWeather.temp)

                        binding.cityName.text = weather.cityName
                        //binding.weatherTemp.text = String.format("%.1f", weather.mainWeather.temp)

                        binding.weatherIcon.loadDynamicWeatherImage(
                            weatherInfo.icon,
                            Constants.IMAGE_TYPE.TYPE_LARGE)

                        Log.d(TAG, "onViewCreated: ${weatherInfo.icon}")
                    }



                }

                is ApiResponse.Error -> {
                    Log.d(TAG, "onViewCreated: ERROR PREUZIMANJA ${response.message}")


                }

            }
        }

       /* viewModel.currentWeather?.observe(viewLifecycleOwner) { response ->

            when(response) {

                is ApiResponse.Success -> {

                    Log.d(TAG, "onViewCreated: USPJEŠNO PREUZIMANJE")

                    val weatherResponse = response.data
                    weatherResponse?.let { weather ->

                        val weatherInfo = weather.weatherList[0]

                        binding.cityName.text = weather.cityName
                        binding.weatherTemp.text = String.format("%.1f", weather.mainWeather.temp)
                    }



                }

            }

        }*/

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}