package com.example.forecasttest.forecast.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forecasttest.forecast.business.Forecast
import com.example.forecasttest.location.business.LocationRepository
import com.example.forecasttest.location.data.LocationCallback
import com.example.forecasttest.shared.business.ForecastRepository
import com.example.forecasttest.shared.data.repository.api.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: ForecastRepository,
    private val locationRepository: LocationRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _viewState = MutableLiveData<ForecastViewState>()
    val viewState: LiveData<ForecastViewState>
        get() = _viewState


    fun loadProductList(city: String, days: Int) {
        viewModelScope.launch(dispatcher) {
            _viewState.postValue(ForecastViewState.Loading)
            when (val result = repository.getForecast(city, days)) {
                is Result.Error -> errorHandle()
                is Result.Success -> successHandle(result)
            }
        }
    }

    private fun successHandle(result: Result.Success<Forecast>) {
        _viewState.postValue(
            ForecastViewState.Content(
                result.data.forecast.map {
                    ForecastDayViewState(
                        it.date,
                        it.maxTemp,
                        it.minTemp,
                        it.avgTemp,
                        it.description,
                        it.icon
                    )
                }, result.data.location
            )
        )
    }

    private fun errorHandle() {
        _viewState.postValue(ForecastViewState.Error)
    }

    fun getCurrentLocation(locationCallback: LocationCallback) {
        _viewState.postValue(ForecastViewState.Loading)
        locationRepository.requestCurrentLocation(locationCallback)
    }
}
