package com.example.weather_kotlin.repository

import com.example.weather_kotlin.model.Weather

interface LocalRepository {
    fun getAllHistory():List<Weather>
    fun saveEntity(weather: Weather)
    fun deleteEntityByName(name: String)
}