package com.example.weather_kotlin.viewModel

import com.example.weather_kotlin.model.Weather

sealed class AppState{
    data class Success(val weatherData: Weather): AppState()
    object Loading: AppState()
    data class Error(val error: Throwable) : AppState()
}
