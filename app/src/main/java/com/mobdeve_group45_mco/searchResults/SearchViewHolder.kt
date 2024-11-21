package com.mobdeve_group45_mco.searchResults

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.AddLocationFragment
import com.mobdeve_group45_mco.databinding.ItemSearchBinding
import com.mobdeve_group45_mco.forecast.Forecast
import com.mobdeve_group45_mco.utils.Utils


class SearchViewHolder(
    private val viewBinding: ItemSearchBinding,
    private val fragmentManager: FragmentManager
)  : RecyclerView.ViewHolder(viewBinding.root){
    fun bindData(forecast: Forecast){
        viewBinding.itemSearchTvCity.text = "${forecast.location.name}, ${forecast.location.country_code.uppercase()}"
        viewBinding.itemSearchTvTime.text = forecast.current.time
        viewBinding.itemSearchTvTemperature.text = forecast.current.temperature.toString()
        viewBinding.itemSearchTvWeather.text = Utils.getWeatherDescription(forecast.current.weatherCode)
        if (forecast.daily.isNotEmpty()) {
            val currentDay = forecast.daily[0]
            viewBinding.itemSearchTvHl.text = "H:${currentDay.maxTemp}° L:${currentDay.minTemp}°"
        }


        viewBinding.root.setOnClickListener {
                val bottomSheet: AddLocationFragment =
                    AddLocationFragment()
                bottomSheet.show(
                    fragmentManager,
                    "ModalBottomSheet")
            }
        }
    }
