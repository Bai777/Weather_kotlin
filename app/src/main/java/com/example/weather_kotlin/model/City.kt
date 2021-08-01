package com.example.weather_kotlin.model

import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val city: String,
    val lat: Double,
    val lon: Double
)
