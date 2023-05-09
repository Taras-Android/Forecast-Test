package com.example.forecasttest.forecast.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forecasttest.databinding.ForecastFragmentBinding
import com.example.forecasttest.location.data.LocationCallback
import com.example.forecasttest.shared.presentation.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : Fragment() {
    private lateinit var binding: ForecastFragmentBinding
    private val viewModel: ForecastViewModel by viewModels()
    private val adapter = ForecastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ForecastFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductRecyclerView()

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            updateUI(viewState)
        }
        viewModel.getCurrentLocation((object : LocationCallback {
            override fun onLocationReceived(latitude: Double, longitude: Double) {
                viewModel.loadProductList("$latitude,$longitude", 14)
            }
        }))

         val activityViewModel: MainActivityViewModel by activityViewModels()
        activityViewModel.setSearchListener(viewModel.getSearchListener())
    }

    private fun updateUI(viewState: ForecastViewState) {
        when (viewState) {
            is ForecastViewState.Content -> {
                binding.viewForecast.isVisible = true
                binding.errorView.isVisible = false
                binding.loadingView.isVisible = false
                adapter.setData(viewState.productList)
                requireActivity().title = viewState.location.name
            }
            ForecastViewState.Error -> {
                binding.viewForecast.isVisible = false
                binding.errorView.isVisible = true
                binding.loadingView.isVisible = false
            }
            ForecastViewState.Loading -> {
                binding.viewForecast.isVisible = false
                binding.errorView.isVisible = false
                binding.loadingView.isVisible = true
            }
        }
    }

    private fun setupProductRecyclerView() {
        binding.viewForecast.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.viewForecast.adapter = adapter
    }
}