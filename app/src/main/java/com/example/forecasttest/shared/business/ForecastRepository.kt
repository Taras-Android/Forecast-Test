package com.example.forecasttest.shared.business

import com.example.forecasttest.forecast.business.Forecast
import com.example.forecasttest.shared.data.repository.api.Result

interface ForecastRepository {

    suspend fun getForecast(city: String, days: Int): Result<Forecast>
}