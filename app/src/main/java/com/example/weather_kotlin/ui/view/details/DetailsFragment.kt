package com.example.weather_kotlin.ui.view.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weather_kotlin.R
import com.example.weather_kotlin.databinding.FragmentDetailsBinding
import com.example.weather_kotlin.model.City
import com.example.weather_kotlin.utils.CircleTransformation
import com.example.weather_kotlin.utils.showSnackBar
import com.example.weather_kotlin.viewModel.AppState
import com.example.weather_kotlin.viewModel.DetailsViewModel
import com.squareup.picasso.Picasso
import com.example.weather_kotlin.model.Weather as Weather1


class DetailsFragment : Fragment() {

    private lateinit var weatherBundle: Weather1
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() : FragmentDetailsBinding {
            return _binding!!
        }

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(
            DetailsViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Weather1>(BUNDLE_EXTRA)?.apply {
            weatherBundle = this
        }!!
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat, weatherBundle.city.lon)
    }

    private fun setWeather(weather: Weather1) {
        binding.mainView.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE

        val city = weatherBundle.city
        saveCity(city, weather)



        weatherBundle.let {
            binding.cityName.text = weatherBundle.city.name
            binding.cityCoordinates.text = "${weatherBundle.city.lat} ${weatherBundle.city.lon}"
            binding.temperatureValue.text = weather.temperature.toString()
            binding.feelsLikeValue.text = weather.feelsLike.toString()
            binding.condition.text = weather.condition

            Picasso
                .get()
                .load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
                .transform(CircleTransformation())
//                .rotate(90f)
                .into(binding.headerIcon)


//             Glide.with(binding.weatherIcon)
//                     .load("https://yastatic.net/weather/i/icons/blueye/color/svg/${it}.svg")
//                     .into(binding.weatherIcon)

//            binding.weatherIcon.load("https://yastatic.net/weather/i/icons/blueye/color/svg/${it}.svg")



        }

//        GlideToVectorYou.justLoadImage(
//            activity,
//            Uri.parse("https://yastatic.net/weather/i/icons/blueye/color/svg/${it}.svg"),
//            weatherIcon
//        )


    }

    private fun saveCity(city: City, weather: Weather1) {
        viewModel.saveCityToDB(
            Weather1(
                city,
                weather.temperature,
                weather.feelsLike,
                weather.condition
            )
        )
    }


    fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                setWeather(appState.weatherData[0])
            }
            is AppState.Loading -> {
                binding.mainView.visibility = View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.mainView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                {
                    viewModel.getWeatherFromRemoteSource(
                        weatherBundle.city.lat,
                        weatherBundle.city.lon
                    )
                })

            }


        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}















