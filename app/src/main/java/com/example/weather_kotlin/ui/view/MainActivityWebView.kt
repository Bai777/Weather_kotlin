package com.example.weather_kotlin.ui.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_kotlin.databinding.MainActivityWebviewBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MainActivityWebView : AppCompatActivity() {

    lateinit var binding: MainActivityWebviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ok.setOnClickListener(clickListener)
    }

    val clickListener: View.OnClickListener = object : View.OnClickListener {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun onClick(v: View?) {

            Thread {
                try {
                    var httpsURLConnection: HttpsURLConnection? = null
                    val url = URL(binding.url.text.toString())
                    httpsURLConnection = url.openConnection() as HttpsURLConnection
                    httpsURLConnection.requestMethod = "GET"
                    httpsURLConnection.connectTimeout = 5000

                    val reader = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                    val result = reader.lines().collect(Collectors.joining("\n"))
                    runOnUiThread{
                        binding.webview.loadDataWithBaseURL(null, result, "text/html; charset=utf-8", "utf-8", null)
                    }
                } catch (e: Exception) {
                    Log.d("log", e.localizedMessage)
                }
            }.start()
        }
    }
}