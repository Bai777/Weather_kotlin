package com.example.weather_kotlin.repository

import com.example.weather_kotlin.model.Weather
import com.example.weather_kotlin.room.HistoryDAO
import com.example.weather_kotlin.utils.convertHistoryEntityToWeather
import com.example.weather_kotlin.utils.convertWeatherToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDAO)
    : LocalRepository {


    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }

}
