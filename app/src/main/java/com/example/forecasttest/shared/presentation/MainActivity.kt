package com.example.forecasttest.shared.presentation

import android.Manifest
import android.app.SearchManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.forecasttest.R
import com.example.forecasttest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()

    private val queryTextListener: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            viewModel.searchLocation(query?: "")

            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkLocationPermission()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(queryTextListener)
        return super.onCreateOptionsMenu(menu)
    }

    private fun checkLocationPermission() {
        if (!hasLocationPermission()) {
            requestLocationPermission()
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }
}