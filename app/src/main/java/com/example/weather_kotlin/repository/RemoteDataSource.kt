package com.example.weather_kotlin.repository

import android.os.Parcel
import android.os.Parcelable
import com.example.weather_kotlin.model.YANDEX_API_KEY_NAME
import com.example.weather_kotlin.model.YANDEX_API_URI
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.*

import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import com.example.weather_kotlin.model.WeatherDTO as WeatherDTO1


// класс запроса на сервер
class RemoteDataSource {

    private val weatherApi = Retrofit.Builder()
        .baseUrl(YANDEX_API_URI)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()

            )
        )
        .client(createOkHttpClient(WeatherApiInterceptor()))
        .build().create(WeatherAPI::class.java)

    inner class WeatherApiInterceptor: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.proceed(chain.request())
        }
    }


    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }



    fun getWeatherDetails(
        lat: Double,
        lon: Double,
        callback: Callback<WeatherDTO1>
    ) {
        weatherApi.getWeather(YANDEX_API_KEY_NAME, lat, lon).enqueue(callback)
    }





    }