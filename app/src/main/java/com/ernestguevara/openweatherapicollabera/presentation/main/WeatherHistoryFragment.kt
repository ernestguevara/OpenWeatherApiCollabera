package com.ernestguevara.openweatherapicollabera.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ernestguevara.openweatherapicollabera.R
import com.ernestguevara.openweatherapicollabera.databinding.FragmentWeatherHistoryBinding
import com.ernestguevara.openweatherapicollabera.presentation.WeatherViewModel
import com.ernestguevara.openweatherapicollabera.presentation.adapters.WeatherAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class WeatherHistoryFragment : Fragment() {

    private val weatherViewModel: WeatherViewModel by activityViewModels()
    lateinit var binding: FragmentWeatherHistoryBinding

    @Inject
    lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        observeViewModel()
    }

    private fun setupRV() = binding.rvWeathers.apply {
        adapter = weatherAdapter
        layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun observeViewModel() {
        weatherViewModel.getWeatherHistoryValue.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                binding.rvWeathers.visibility = View.GONE
                binding.tvError.visibility = View.VISIBLE
            } else {
                binding.rvWeathers.visibility = View.VISIBLE
                binding.tvError.visibility = View.GONE
                weatherAdapter.weatherList = list.map {
                    it.toWeatherModel()
                }
            }
        }
    }
}