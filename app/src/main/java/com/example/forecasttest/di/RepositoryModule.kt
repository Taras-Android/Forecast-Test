package com.example.forecasttest.di

import com.example.forecasttest.shared.business.ForecastRepository
import com.example.forecasttest.shared.data.repository.api.ApiClient
import com.example.forecasttest.shared.data.repository.api.ForecastRepositoryAPI
import com.example.forecasttest.shared.data.repository.api.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}