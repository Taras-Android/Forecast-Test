package com.example.forecasttest.di

import android.content.Context
import android.location.LocationManager
import com.example.forecasttest.location.business.LocationRepository
import com.example.forecasttest.location.data.LocationRepositoryImpl
import com.example.forecasttest.shared.business.ForecastRepository
import com.example.forecasttest.shared.data.repository.api.ApiClient
import com.example.forecasttest.shared.data.repository.api.ForecastRepositoryAPI
import com.example.forecasttest.shared.data.repository.api.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesWeatherService(): WeatherService = ApiClient.getService()

    @Provides
    fun providesForecastRepositoryAPI(
        service: WeatherService
    ): ForecastRepositoryAPI = ForecastRepositoryAPI(service)

    @Provides
    fun providesForecastRepository(
        forecastRepositoryAPI: ForecastRepositoryAPI
    ): ForecastRepository = forecastRepositoryAPI

    @Provides
    fun providesLocationManager(
        @ApplicationContext context: Context
    ): LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @Provides
    fun providesLocationRepositoryImpl(
        locationManager: LocationManager
    ) : LocationRepositoryImpl = LocationRepositoryImpl(locationManager)

    @Provides
    fun providesLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ) : LocationRepository = locationRepositoryImpl

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}