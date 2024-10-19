package com.mobdeve_group45_mco.HourlyWeather

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.databinding.HourlyWeatherLayoutBinding

class HourlyViewHolder(private val viewBinding: HourlyWeatherLayoutBinding) : RecyclerView.ViewHolder(viewBinding.root) {
    fun bindData(hourlyWeather: HourlyWeather){
        this.viewBinding.hourlyWeatherTvHour.text = hourlyWeather.hour
        this.viewBinding.hourlyWeatherIvIcon.setImageResource(hourlyWeather.iconId)
        this.viewBinding.hourlyWeatherTvTemp.text = hourlyWeather.temp
    }
}