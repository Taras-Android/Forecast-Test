package com.example.forecasttest.forecast.presentation

sealed class ForecastViewState {
    object Loading : ForecastViewState()
    object Error : ForecastViewState()
    data class Content(val productList: List<ForecastDayViewState>) : ForecastViewState()
}