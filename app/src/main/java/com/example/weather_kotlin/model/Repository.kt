package com.example.weather_kotlin.model

interface Repository {
    fun getWeatherFromServer(): Weather

    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}