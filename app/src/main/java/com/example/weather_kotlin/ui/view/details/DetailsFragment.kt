package com.example.weather_kotlin.ui.view.details

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weather_kotlin.databinding.FragmentDetailsBinding
import com.example.weather_kotlin.model.FactDTO
import com.example.weather_kotlin.model.WeatherDTO
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.DETAILS_CONDITION_EXTRA
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.DETAILS_FEELS_LIKE_EXTRA
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.DETAILS_INTENT_FILTER
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.DETAILS_LOAD_RESULT_EXTRA
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.DETAILS_RESPONSE_SUCCESS_EXTRA
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.DETAILS_TEMP_EXTRA
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.LATITUDE_EXTRA
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.LONGITUDE_EXTRA
import kotlinx.android.synthetic.main.fragment_details.*
import com.example.weather_kotlin.model.Weather as Weather1

class DetailsFragment : Fragment() {

    companion object {

        const val BUNDLE_EXTRA = "weather"
        var weatherBundle: Weather1? = null

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var weatherLocal: Weather1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            intent?.let {
                when (it.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                    DETAILS_RESPONSE_SUCCESS_EXTRA ->
                        renderData(
                            WeatherDTO(
                                FactDTO(
                                    it.getIntExtra(DETAILS_TEMP_EXTRA, -1),
                                    it.getIntExtra(DETAILS_FEELS_LIKE_EXTRA, -1),
                                    it.getStringExtra(DETAILS_CONDITION_EXTRA)!!
                                )
                            )
                        )
                    else -> null
                }
            }

        }


    }

    @SuppressLint("SetTextI18n")
    fun renderData(weatherDTO: WeatherDTO) {
        binding?.let {
            mainView.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE
        }

        weatherBundle?.let { weatherBundle: Weather1 ->
            binding.cityCoordinates.text = "${weatherBundle.city.lat} ${weatherBundle.city.lon}"
            binding.cityName.text = Companion.weatherBundle?.city?.city
            binding.feelsLikeValue.text = weatherDTO.fact.temp.toString()
            binding.temperatureValue.text = weatherDTO.fact.feels_like.toString()
            binding.condition.text = weatherDTO.fact.condition
        }
    }

    fun getWeather() {
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        weatherBundle?.let {
            context?.startService(Intent(context, DetailsService::class.java).apply {
                putExtra(LATITUDE_EXTRA, it.city.lat)
                putExtra(LONGITUDE_EXTRA, it.city.lon)
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Weather1>(BUNDLE_EXTRA)?.apply {
            weatherBundle = this
            getWeather()
        }

    }
}


