package com.mobdeve_group45_mco.dailyWeather

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.databinding.ItemDailyWeatherLayoutBinding

class DailyViewHolder(private val viewBinding: ItemDailyWeatherLayoutBinding) : RecyclerView.ViewHolder(viewBinding.root){
    fun bindData(dailyWeather: DailyWeather){
        this.viewBinding.dailyWeatherTvDay.text = dailyWeather.day
        this.viewBinding.dailyWeatherTvIcon.setImageResource(dailyWeather.iconId)
        this.viewBinding.dailyWeatherTvTempRange.text = dailyWeather.tempRange
    }
}