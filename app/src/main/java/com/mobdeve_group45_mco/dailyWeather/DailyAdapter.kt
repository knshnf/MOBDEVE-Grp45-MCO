package com.mobdeve_group45_mco.dailyWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.databinding.ItemDailyWeatherLayoutBinding

class DailyAdapter(private val dailyWeather: ArrayList<DailyWeather>): RecyclerView.Adapter<DailyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {

        val itemViewBinding: ItemDailyWeatherLayoutBinding = ItemDailyWeatherLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return DailyViewHolder(itemViewBinding)
    }

    override fun getItemCount(): Int {
        return dailyWeather.size
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.bindData(dailyWeather[position])
    }
}