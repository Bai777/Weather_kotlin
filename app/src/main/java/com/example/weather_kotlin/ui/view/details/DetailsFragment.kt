package com.example.weather_kotlin.ui.view.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.weather_kotlin.R
import com.example.weather_kotlin.databinding.FragmentDetailsBinding
import com.example.weather_kotlin.model.WeatherDTO
import com.example.weather_kotlin.model.Weather as Weather1

class DetailsFragment : Fragment(), WeatherLoaderListener() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    var weather


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onLoaded(weatherDTO: WeatherDTO) {
       with(binding){
            cityCoordinates.text="${city.lat}${city.lon}"
       }
    }

    override fun onFailed(throwable: Throwable) {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Weather1>(BUNDLE_EXTRA)?.apply {
            WeatherLoader(this@DetailsFragment, city.lat, city.lon).loadWeather()
        }

//        arguments?.getParcelable<Weather1>(BUNDLE_EXTRA)?.let { weather ->
//            weather.city.also { city ->
//                binding.cityName.text = city.city
//                binding.cityCoordinates.text = String.format(
//                    getString(R.string.city_coordinates),
//                    city.lat.toString(),
//                    city.lon.toString()
//                )
//                binding.temperatureValue.text = weather.temperature.toString()
//                binding.feelsLikeValue.text = weather.feelsLike.toString()
//
//            }
//
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {

        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}

