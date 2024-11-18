package com.mobdeve_group45_mco.dailyWeather

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.R
import com.mobdeve_group45_mco.databinding.ItemDailyWeatherLayoutBinding
import com.mobdeve_group45_mco.utils.Utils

class DailyViewHolder(private val viewBinding: ItemDailyWeatherLayoutBinding) : RecyclerView.ViewHolder(viewBinding.root){
    fun bindData(dailyWeather: DailyWeather){
        this.viewBinding.dailyWeatherTvDay.text = Utils.getDayOfWeek(dailyWeather.time)
        this.viewBinding.dailyWeatherTvIcon.setImageResource(Utils.getWeatherIconByCode(dailyWeather.weatherCode))
        this.viewBinding.dailyWeatherTvTempRange.text =
            dailyWeather.minTemp.toString() + "° - " +
                    dailyWeather.maxTemp.toString() + "°"
    }
}