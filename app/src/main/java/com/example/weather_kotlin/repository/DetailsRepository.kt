package com.example.weather_kotlin.repository

import com.example.weather_kotlin.model.WeatherDTO

interface DetailsRepository {
    fun getWeatherDetailsFromServer(lat: Double,
                                    lon: Double,
                                    callback: retrofit2.Callback<WeatherDTO>)
}