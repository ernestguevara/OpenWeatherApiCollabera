package com.ernestguevara.openweatherapicollabera.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ernestguevara.openweatherapicollabera.BuildConfig
import com.ernestguevara.openweatherapicollabera.R
import com.ernestguevara.openweatherapicollabera.databinding.ItemWeatherBinding
import com.ernestguevara.openweatherapicollabera.domain.model.WeatherModel
import com.ernestguevara.openweatherapicollabera.util.convertLongToTimeString
import com.ernestguevara.openweatherapicollabera.util.getCurrentDate
import com.ernestguevara.openweatherapicollabera.util.setLocationName
import javax.inject.Inject

class WeatherAdapter @Inject constructor(
    private val context: Context,
    private val glide: RequestManager
) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<WeatherModel>() {
        override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var weatherList: List<WeatherModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWeatherBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherHistory = weatherList[position]
        holder.binding.apply {
            weatherHistory.let {
                tvLocation.text = setLocationName(it.name, it.sysDTO?.country)
                tvDate.text = getCurrentDate()
                tvTemp.text =
                    context.getString(R.string.val_temp, it.mainDTO?.temp)
                tvDesc.text = it.weatherResultsDTO?.description
                tvSunrise.text =
                    context.getString(R.string.val_sunrise, convertLongToTimeString(it.sysDTO?.sunrise))
                tvSunset.text =
                    context.getString(R.string.val_sunset, convertLongToTimeString(it.sysDTO?.sunset))
                tvTempMin.text =
                    context.getString(R.string.val_temp_min, it.mainDTO?.tempMin)
                tvTempMax.text = context.getString(R.string.val_temp_max, it.mainDTO?.tempMax)

                glide.load("${BuildConfig.IMG_URL}img/wn/${it.weatherResultsDTO?.icon}.png")
                    .placeholder(R.drawable.ic_settings_24)
                    .into(ivIcon)

                glide.load(R.drawable.moon)
                    .into(ivIndicator)
            }
        }
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }
}