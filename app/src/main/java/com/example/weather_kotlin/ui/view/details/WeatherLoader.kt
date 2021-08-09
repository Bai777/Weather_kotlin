package com.example.weather_kotlin.ui.view.details

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weather_kotlin.model.WeatherDTO
import com.example.weather_kotlin.model.YANDEX_API_KEY_NAME
import com.example.weather_kotlin.model.YANDEX_API_KEY_VALUE
import com.example.weather_kotlin.model.YANDEX_API_URI
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(
    private val listener: WeatherLoaderListener,
    private val lat: Double,
    private val lon: Double
) {
    @RequiresApi(Build.VERSION_CODES.N)
    fun loadWeather() {
        Thread {
            try {
                val url = URL("${YANDEX_API_URI}?lat=${lat}&lon=${lon}")
                val httpsURLConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
                httpsURLConnection.connectTimeout = 5000
                httpsURLConnection.requestMethod = "GET"
                httpsURLConnection.addRequestProperty(YANDEX_API_KEY_NAME, YANDEX_API_KEY_VALUE)
                val buffer = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                val weatherDTO:WeatherDTO= Gson().fromJson(buffer, WeatherDTO::class.java)
                listener.onLoaded(weatherDTO)
            }catch (e:Exception){
                listener.onFailed(e)
            }



        }.start()

    }
}

interface WeatherLoaderListener {
    fun onLoaded(weatherDTO: WeatherDTO)
    fun onFailed(throwable: Throwable)
}