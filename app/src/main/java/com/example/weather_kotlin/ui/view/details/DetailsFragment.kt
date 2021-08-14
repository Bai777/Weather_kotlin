package com.example.weather_kotlin.ui.view.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather_kotlin.R
import com.example.weather_kotlin.databinding.FragmentDetailsBinding
import com.example.weather_kotlin.viewModel.AppState
import com.example.weather_kotlin.viewModel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import okhttp3.*
import com.example.weather_kotlin.model.Weather as Weather1

class DetailsFragment : Fragment() {


    companion object {

        private var _binding: FragmentDetailsBinding? = null
        private val binding get() = _binding!!
        private lateinit var weatherBundle: Weather1


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
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    private val viewModel: DetailsViewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         arguments?.getParcelable<Weather1>(BUNDLE_EXTRA)?.apply {
            weatherBundle = this
        }!!
        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat, weatherBundle.city.lon)
    }

    private fun setWeather(weather: Weather1) {
        binding.mainView.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE


       weatherBundle?.let {
           binding.cityName.text = weatherBundle?.city.name
           binding.cityCoordinates.text = "${weatherBundle.city.lat} ${weatherBundle.city.lon}"
           binding.temperatureValue.text = weather.temperature.toString()
           binding.feelsLikeValue.text = weather.feelsLike.toString()
           binding.condition.text = weather.condition
       }

    }

    @SuppressLint("SetTextI18n")
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
//                mainView.showSnackBar(
//                    getString(R.string.error),
//                    getString(R.string.reload)
//                ) {
//                    viewModel.getWeatherFromRemoteSource(
//                        weatherBundle.city.lat,
//                        weatherBundle.city.lon
//                    )
//                }

            }


        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}












