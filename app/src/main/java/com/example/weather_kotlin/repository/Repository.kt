package com.example.weather_kotlin.repository

import com.example.weather_kotlin.model.Weather

interface Repository {
    fun getWeatherFromServer(): Weather

    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}