package com.example.forecasttest.forecast.presentation

import com.example.forecasttest.forecast.business.Location

sealed class ForecastViewState {
    object Loading : ForecastViewState()
    object Error : ForecastViewState()
    data class Content(val productList: List<ForecastDayViewState>, val location: Location) : ForecastViewState()
}