package com.example.forecasttest.location.business

import com.example.forecasttest.location.data.LocationCallback

interface LocationRepository {
     fun requestCurrentLocation(callback: LocationCallback)
}