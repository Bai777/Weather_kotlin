package com.example.weather_kotlin.ui.view

sealed class AppState{
    data class Success(val weatherData: Any): AppState()
    object Loading: AppState()
    data class Error(val error: Throwable) : AppState()
}
