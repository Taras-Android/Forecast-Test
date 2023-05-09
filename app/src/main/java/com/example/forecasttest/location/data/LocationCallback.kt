package com.example.forecasttest.location.data

interface LocationCallback {
    fun onLocationReceived(latitude: Double, longitude: Double)
}