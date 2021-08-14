package com.example.weather_kotlin.utils

import com.example.weather_kotlin.model.FactDTO
import com.example.weather_kotlin.model.Weather
import com.example.weather_kotlin.model.WeatherDTO
import com.example.weather_kotlin.model.getDefaultCity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.fact!!
    return listOf(Weather(getDefaultCity(), fact.temp!!, fact.feels_like!!, fact.condition!!))
}
