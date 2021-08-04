package com.example.weather_kotlin.model

class RepositoryImpl : Repository {


    override fun getWeatherFromServer() = Weather()

    override fun getWeatherFromLocalStorageRus() = getRussianCities()


override fun getWeatherFromLocalStorageWorld() = getWorldCities()

}