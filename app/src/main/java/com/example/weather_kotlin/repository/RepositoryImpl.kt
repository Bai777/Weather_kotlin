package com.example.weather_kotlin.repository

import com.example.weather_kotlin.model.Weather
import com.example.weather_kotlin.model.getRussianCities
import com.example.weather_kotlin.model.getWorldCities

class RepositoryImpl : Repository {


    override fun getWeatherFromServer() = Weather()

    override fun getWeatherFromLocalStorageRus() = getRussianCities()


override fun getWeatherFromLocalStorageWorld() = getWorldCities()

}