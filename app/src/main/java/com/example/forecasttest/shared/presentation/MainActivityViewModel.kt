package com.example.forecasttest.shared.presentation

import androidx.lifecycle.ViewModel
import com.example.forecasttest.search.data.SearchListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    private lateinit var searchListener: SearchListener

    fun searchLocation(query: String) {
        searchListener.onStartSearch(query)
    }

    fun setSearchListener(searchListener: SearchListener) {
        this.searchListener = searchListener
    }
}