package com.example.forecasttest.location.data

import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.example.forecasttest.location.business.LocationRepository
import com.example.forecasttest.shared.data.repository.api.Result

class LocationRepositoryImpl(private val locationManager: LocationManager) : LocationRepository {

    private lateinit var locationCallback: LocationCallback
    private val locationListener = object : LocationListener{

        override fun onLocationChanged(p0: Location) {
            locationCallback.onLocationReceived(p0.latitude, p0.longitude)
            locationManager.removeUpdates(this)
        }

        @Deprecated("Deprecated in Java")
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}
    }

    override fun requestCurrentLocation(callback: LocationCallback) {
            try {
                locationCallback = callback
                val provider = locationManager.getBestProvider(Criteria(), false)
                    ?: LocationManager.NETWORK_PROVIDER
                val location = locationManager.getLastKnownLocation(provider)

                if (location != null) {
                    locationListener.onLocationChanged(location)
                } else {
                    locationManager.requestLocationUpdates(provider, 0L, 0f, locationListener)
                }

            } catch (e: SecurityException) {
                Result.Error(SecurityException("Location permission not granted"))
            } catch (e: Exception) {
                Result.Error(e)
            }
    }

}