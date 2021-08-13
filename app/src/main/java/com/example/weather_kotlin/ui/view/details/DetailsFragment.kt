package com.example.weather_kotlin.ui.view.details

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weather_kotlin.BuildConfig
import com.example.weather_kotlin.R
import com.example.weather_kotlin.databinding.FragmentDetailsBinding
import com.example.weather_kotlin.model.FactDTO
import com.example.weather_kotlin.model.WeatherDTO
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.DETAILS_CONDITION_EXTRA
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.DETAILS_FEELS_LIKE_EXTRA
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.DETAILS_INTENT_FILTER
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.DETAILS_LOAD_RESULT_EXTRA
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.DETAILS_RESPONSE_SUCCESS_EXTRA
import com.example.weather_kotlin.ui.view.details.DetailsService.Companion.DETAILS_TEMP_EXTRA
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_details.*
import okhttp3.*
import java.io.IOException
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

        private const val PROCESS_ERROR = "Обработка ошибки"
        private const val MAIN_LINK = "https://api.weather.yandex.ru/v2/informers?"
        private const val REQUEST_API_KEY = "X-Yandex-API-Key"
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

        val fact = weatherDTO.fact
        if (fact == null || fact.temp == null || fact.feels_like == null || fact.condition.isNullOrEmpty()){
            Toast.makeText(context, "Error: $fact", Toast.LENGTH_LONG).show()
        }else{
            val city = weatherBundle?.city
            binding.cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                city?.lat.toString(),
                city?.lon.toString()
            )

            binding.cityName.text = city?.city
            binding.feelsLikeValue.text = fact.feels_like.toString()
            binding.temperatureValue.text = fact.temp.toString()
            binding.condition.text = fact.condition
        }
    }

    private fun getWeather() {
        mainView.visibility = View.GONE
        loadingLayout.visibility = View.VISIBLE

        val client = OkHttpClient()// Клиент
        val builder: Request.Builder = Request.Builder() //Создаём строителя запроса
        builder.header(REQUEST_API_KEY, BuildConfig.YANDEX_API_KEY_NAME)
        builder.url(MAIN_LINK + "lat=${weatherBundle?.city?.lat}&lon=${weatherBundle?.city?.lon}") // Формируем URL
        val request: Request = builder.build()// Создаём запрос
        val call: Call = client.newCall(request)// Ставим запрос в очередь и отправляем
        call.enqueue(object : Callback {
            val handler: Handler = Handler()

            // Вызывается, если ответ от сервера пришёл
            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val serverResponse: String? = response.body()?.string()
                // Синхронизируем поток с потоком UI
                if (response.isSuccessful && serverResponse != null) {
                    handler.post {
                        renderData(Gson().fromJson(serverResponse, WeatherDTO::class.java))
                    }
                } else if (!response.isSuccessful) {
                    Toast.makeText(context, "response " + response, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "serverResponse " + serverResponse, Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(context, "call $call : e $e", Toast.LENGTH_LONG).show()
            }

        })

//        weatherBundle?.let {
//            context?.startService(Intent(context, DetailsService::class.java).apply {
//                putExtra(LATITUDE_EXTRA, it.city.lat)
//                putExtra(LONGITUDE_EXTRA, it.city.lon)
//            })
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Weather1>(BUNDLE_EXTRA)?.apply {
            weatherBundle = this
            getWeather()
        }

    }
}


