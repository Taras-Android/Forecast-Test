package com.example.forecasttest.forecast.data

data class WeatherEntity(
    val error: ErrorEntity?,
    val location: LocationEntity,
    val forecast: ForecastEntity
)