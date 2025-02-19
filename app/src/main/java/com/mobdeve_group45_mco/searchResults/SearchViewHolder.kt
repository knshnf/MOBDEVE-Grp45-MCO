package com.mobdeve_group45_mco.searchResults

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.AddLocationFragment
import com.mobdeve_group45_mco.databinding.ItemSearchBinding
import com.mobdeve_group45_mco.forecast.Forecast
import com.mobdeve_group45_mco.utils.Utils
import java.text.SimpleDateFormat
import java.util.Locale


class SearchViewHolder(
    private val viewBinding: ItemSearchBinding,
    private val fragmentManager: FragmentManager
) : RecyclerView.ViewHolder(viewBinding.root) {
    fun bindData(forecast: Forecast) {
        viewBinding.itemSearchTvCity.text = "${forecast.location.name}, ${forecast.location.country_code.uppercase()}"
        viewBinding.itemSearchTvTime.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault()).parse(forecast.current.time))
        viewBinding.itemSearchTvTemperature.text = "${forecast.current.temperature}°"
        viewBinding.itemSearchTvWeather.text = Utils.getWeatherDescription(forecast.current.weatherCode)
        if (forecast.daily.isNotEmpty()) {
            val currentDay = forecast.daily[0]
            viewBinding.itemSearchTvHl.text = "H:${currentDay.maxTemp}° L:${currentDay.minTemp}°"
        }

        viewBinding.root.setOnClickListener {
            // Pass the Forecast to AddLocationFragment
            val bottomSheet = AddLocationFragment.newInstance(forecast)
            bottomSheet.show(fragmentManager, "ModalBottomSheet")
        }
    }
}
