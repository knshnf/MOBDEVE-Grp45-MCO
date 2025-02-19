package com.mobdeve_group45_mco.hourlyWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.databinding.ItemHourlyWeatherLayoutBinding

class HourlyAdapter(private val hourlyWeather: ArrayList<HourlyWeather>): RecyclerView.Adapter<HourlyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {

        val itemViewBinding: ItemHourlyWeatherLayoutBinding = ItemHourlyWeatherLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return HourlyViewHolder(itemViewBinding)
    }

    override fun getItemCount(): Int {
        return hourlyWeather.size
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bindData(hourlyWeather[position])
    }
}
