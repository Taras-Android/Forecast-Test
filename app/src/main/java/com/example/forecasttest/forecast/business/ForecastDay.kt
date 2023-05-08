package com.example.forecasttest.forecast.business

data class ForecastDay(
    val date: String,
    val maxTemp: Float,
    val minTemp: Float,
    val avgTemp: Float,
    val description: String,
    val icon: String
)