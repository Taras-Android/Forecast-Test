package com.example.forecasttest.forecast.data

data class DayEntity(
    val maxtemp_c: Float,
    val mintemp_c: Float,
    val avgtemp_c: Float,
    val condition: ConditionEntity
)