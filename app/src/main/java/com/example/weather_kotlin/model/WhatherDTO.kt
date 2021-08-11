package com.example.weather_kotlin.model

data class WeatherDTO(val fact: FactDTO)

data class FactDTO(val feels_like: Int, val temp: Int, val condition: String)