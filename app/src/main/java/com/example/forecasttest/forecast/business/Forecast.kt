package com.example.forecasttest.forecast.business

data class Forecast(
    val location: Location,
    val forecast: List<ForecastDay>
)
