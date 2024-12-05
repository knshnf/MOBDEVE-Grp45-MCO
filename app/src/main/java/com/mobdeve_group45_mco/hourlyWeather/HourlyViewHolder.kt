package com.mobdeve_group45_mco.hourlyWeather

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.databinding.ItemHourlyWeatherLayoutBinding
import com.mobdeve_group45_mco.utils.Utils

class HourlyViewHolder(private val viewBinding: ItemHourlyWeatherLayoutBinding) : RecyclerView.ViewHolder(viewBinding.root){
    fun bindData(hourlyWeather: HourlyWeather){
        this.viewBinding.hourlyWeatherTvHour.text = Utils.getHourIn12HourFormat(hourlyWeather.time)
        this.viewBinding.hourlyWeatherIvIcon.setImageResource(Utils.getWeatherIconByCode(hourlyWeather.weatherCode))
        this.viewBinding.hourlyWeatherTvTemp.text = hourlyWeather.temp.toString() + "°"
    }
}