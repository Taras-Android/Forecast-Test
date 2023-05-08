package com.example.forecasttest.forecast.presentation

data class ForecastDayViewState(
    val date: String,
    val maxTemp: Float,
    val minTemp: Float,
    val avgTemp: Float,
    val description: String,
    val icon: String
)