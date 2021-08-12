package com.example.weather_kotlin.ui.view.details

import android.app.IntentService
import android.content.Intent
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weather_kotlin.model.WeatherDTO
import com.example.weather_kotlin.model.YANDEX_API_KEY_NAME
import com.example.weather_kotlin.model.YANDEX_API_KEY_VALUE
import com.example.weather_kotlin.model.YANDEX_API_URI
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailsService(name: String = "DetailService") : IntentService(name) {
    companion object {
        const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
        const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
        const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
        const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
        const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
        const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
        const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
        const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
        const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
        const val DETAILS_TEMP_EXTRA = "TEMPERATURE"
        const val DETAILS_FEELS_LIKE_EXTRA = "FEELS LIKE"
        const val DETAILS_CONDITION_EXTRA = "CONDITION"

        const val LATITUDE_EXTRA = "Latitude"
        const val LONGITUDE_EXTRA = "Longitude"
        private const val REQUEST_GET = "GET"
        private const val REQUEST_TIMEOUT = 10000
        private const val REQUEST_API_KEY = "X-Yandex-API-Key"
        val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            loadWeather(
                it.getDoubleExtra(LATITUDE_EXTRA, -1.0),
                it.getDoubleExtra(LATITUDE_EXTRA, -1.0)
            )
        }

    }

    private fun loadWeather(lat: Double, lon: Double) {

        Thread {
            try {
                val url = URL("$YANDEX_API_URI?lat=${lat}&lon=${lon}")
                val httpsURLConnection: HttpsURLConnection =
                    url.openConnection() as HttpsURLConnection
                httpsURLConnection.connectTimeout = 5000
                httpsURLConnection.requestMethod = "GET"
                httpsURLConnection.addRequestProperty(YANDEX_API_KEY_NAME, YANDEX_API_KEY_VALUE)
                val buffer = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                val fact = weatherDTO.fact
                putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
                broadcastIntent.putExtra(DETAILS_TEMP_EXTRA, fact.temp)
                broadcastIntent.putExtra(DETAILS_FEELS_LIKE_EXTRA, fact.feels_like)
                broadcastIntent.putExtra(DETAILS_CONDITION_EXTRA, fact.condition)
                LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "Something is wrong", Toast.LENGTH_LONG).show()
            }
        }.start()
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)

    }


}