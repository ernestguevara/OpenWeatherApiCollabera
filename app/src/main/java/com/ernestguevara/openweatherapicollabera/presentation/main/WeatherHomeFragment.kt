package com.ernestguevara.openweatherapicollabera.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.RequestManager
import com.ernestguevara.openweatherapicollabera.BuildConfig
import com.ernestguevara.openweatherapicollabera.R
import com.ernestguevara.openweatherapicollabera.databinding.FragmentWeatherHomeBinding
import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel
import com.ernestguevara.openweatherapicollabera.presentation.WeatherViewModel
import com.ernestguevara.openweatherapicollabera.util.convertLongToTimeString
import com.ernestguevara.openweatherapicollabera.util.getCurrentDate
import com.ernestguevara.openweatherapicollabera.util.getCurrentDay
import com.ernestguevara.openweatherapicollabera.util.setLocationName
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class WeatherHomeFragment : Fragment() {
    @Inject
    lateinit var glide: RequestManager

    private val weatherViewModel: WeatherViewModel by activityViewModels()
    lateinit var binding: FragmentWeatherHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherViewModel.getWeatherValue.observe(viewLifecycleOwner) {
            populateView(it)
        }
    }

    private fun populateView(it: WeatherModel?) {
        binding.run {
            if (it == null) {
                tvError.visibility = View.VISIBLE
            } else {
                tvError.visibility = View.GONE

                cardWeather.apply {
                    tvLocation.text = setLocationName(it.name, it.sysDTO?.country)
                    tvDay.text = getCurrentDay()
                    tvDate.text = getCurrentDate()
                    tvTemp.text =
                        getString(R.string.val_temp, it.mainDTO?.temp)
                    tvDesc.text = it.weatherResultsDTO?.description
                    tvSunrise.text =
                        getString(R.string.val_sunrise, convertLongToTimeString(it.sysDTO?.sunrise))
                    tvSunset.text =
                        getString(R.string.val_sunset, convertLongToTimeString(it.sysDTO?.sunset))
                    tvTempMin.text =
                        getString(R.string.val_temp_min, it.mainDTO?.tempMin)
                    tvTempMax.text = getString(R.string.val_temp_max, it.mainDTO?.tempMax)

                    glide.load("${BuildConfig.IMG_URL}img/wn/${it.weatherResultsDTO?.icon}.png")
                        .placeholder(R.drawable.ic_settings_24)
                        .into(ivIcon)

                    glide.load(R.drawable.moon)
                        .into(ivIndicator)
                }
            }
        }

    }
}