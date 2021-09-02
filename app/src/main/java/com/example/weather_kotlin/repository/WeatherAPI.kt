package com.example.weather_kotlin.repository


import com.example.weather_kotlin.model.YANDEX_API_END_POINT
import com.example.weather_kotlin.model.YANDEX_API_KEY_NAME
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import com.example.weather_kotlin.model.WeatherDTO as WeatherDTO1

interface WeatherAPI {
    @GET(YANDEX_API_END_POINT)
    fun getWeather(
        @Header(YANDEX_API_KEY_NAME)token: String,
    @Query("lat")lat:Double,
        @Query("lon")lon:Double
    ): Call<WeatherDTO1>
}