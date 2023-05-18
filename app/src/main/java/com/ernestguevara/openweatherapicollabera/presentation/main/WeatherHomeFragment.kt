package com.ernestguevara.openweatherapicollabera.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.RequestManager
import com.ernestguevara.openweatherapicollabera.R
import com.ernestguevara.openweatherapicollabera.databinding.FragmentWeatherHomeBinding
import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel
import com.ernestguevara.openweatherapicollabera.presentation.auth.AuthActivity
import com.ernestguevara.openweatherapicollabera.util.*
import com.ernestguevara.openweatherapicollabera.util.Constants.DATE_DAY
import com.ernestguevara.openweatherapicollabera.util.Constants.DATE_HOUR
import com.ernestguevara.openweatherapicollabera.util.Constants.DATE_PROPER
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
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

        observeViewModel()

        binding.ivRefresh.setOnClickListener {
            (activity as MainActivity).askPermissions()
        }
    }

    private fun observeViewModel() {
        weatherViewModel.getWeatherValue.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.run {
                    clError.visibility = View.GONE
                    cardWeather.clWeatherContainer.visibility = View.VISIBLE
                }

                populateView(it)
            } else {
                binding.run {
                    clError.visibility = View.VISIBLE
                    cardWeather.clWeatherContainer.visibility = View.GONE
                }
            }

        }

        weatherViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                RequestState.Loading -> (activity as MainActivity).showLoadingDialog()
                RequestState.Failed,
                RequestState.Finished -> (activity as MainActivity).dismissLoadingDialog()
                else -> {}
            }
        }

        weatherViewModel.getWeatherError.observe(viewLifecycleOwner) {
            binding.run {
                Toast.makeText(
                    activity,
                    it,
                    Toast.LENGTH_SHORT
                ).show()
                clError.visibility = View.VISIBLE
                cardWeather.clWeatherContainer.visibility = View.GONE
            }
        }
    }

    private fun populateView(it: WeatherModel?) {
        binding.run {
            if (it == null) {
                clError.visibility = View.VISIBLE
            } else {
                clError.visibility = View.GONE

                cardWeather.apply {
                    tvLocation.text = setLocationName(it.name, it.sysDTO?.country)
                    tvDay.text = convertLongToTimeString(it.localDate, DATE_DAY)
                    tvDate.text = convertLongToTimeString(it.localDate, DATE_PROPER)
                    tvTemp.text =
                        getString(R.string.val_temp, it.mainDTO?.temp)
                    tvDesc.text = it.weatherResultsDTO?.description
                    tvSunrise.text =
                        getString(
                            R.string.val_sunrise,
                            convertLongToTimeString(convertUtcToGmt(it.sysDTO?.sunrise), DATE_HOUR)
                        )
                    tvSunset.text =
                        getString(
                            R.string.val_sunset,
                            convertLongToTimeString(convertUtcToGmt(it.sysDTO?.sunset), DATE_HOUR)
                        )
                    tvTempMin.text =
                        getString(R.string.val_temp_min, it.mainDTO?.tempMin)
                    tvTempMax.text = getString(R.string.val_temp_max, it.mainDTO?.tempMax)

                    glide.load(getIconUrl(it.weatherResultsDTO?.icon))
                        .placeholder(R.drawable.ic_settings_24)
                        .into(ivIcon)

                    loadIndicatorImage(
                        it.localDate,
                        convertUtcToGmt(it.sysDTO?.sunset),
                        glide,
                        ivIndicator
                    )
                }
            }
        }

    }
}