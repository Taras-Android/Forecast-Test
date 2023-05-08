package com.example.forecasttest.shared.data.repository.api

import com.example.forecasttest.forecast.data.LocationEntity
import com.example.forecasttest.forecast.data.WeatherEntity
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherService {

    @GET("forecast.json")
    suspend fun getForecast(@QueryMap params: Map<String, String>): WeatherEntity

    @GET("search.json")
    suspend fun getLocation(@QueryMap params: Map<String, String>): List<LocationEntity>
}