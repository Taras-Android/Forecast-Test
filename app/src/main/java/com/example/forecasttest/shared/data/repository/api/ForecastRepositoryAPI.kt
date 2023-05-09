package com.example.forecasttest.shared.data.repository.api

import com.example.forecasttest.forecast.business.Forecast
import com.example.forecasttest.forecast.business.ForecastDay
import com.example.forecasttest.forecast.business.Location
import com.example.forecasttest.forecast.data.LocationEntity
import com.example.forecasttest.shared.business.ForecastRepository
import com.example.forecasttest.shared.data.repository.api.Result.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastRepositoryAPI @Inject constructor(private val service: WeatherService) :
    ForecastRepository {

    private val KEY = "a869859b803c4bc3829172719230705"
    override suspend fun getForecast(city: String, days: Int): Result<Forecast> {
        return withContext(Dispatchers.IO) {
            try {
                val data = service.getForecast(generateParams(city, days))
                val result = Forecast(mapLocation(data.location), data.forecast.forecastday.map {
                    ForecastDay(
                        it.date,
                        it.day.maxtemp_c,
                        it.day.mintemp_c,
                        it.day.avgtemp_c,
                        it.day.condition.text,
                        it.day.condition.icon
                    )
                })
                Success(result)
            } catch (exception: Exception) {
                Error(exception)
            }
        }
    }



    private fun generateParams(city: String, days: Int) : Map<String, String>{
        val params: HashMap<String, String> = HashMap()
        params[Queries.KEY] = KEY
        params[Queries.QUERY] = city
        params[Queries.DAYS] = days.toString()
        return params
    }

    private fun mapLocation(locationEntity: LocationEntity) : Location =
        Location(locationEntity.name, locationEntity.country)
}