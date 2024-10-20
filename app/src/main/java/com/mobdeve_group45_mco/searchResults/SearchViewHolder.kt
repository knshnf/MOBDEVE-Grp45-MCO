package com.mobdeve_group45_mco.searchResults

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.databinding.ItemSearchBinding

class SearchViewHolder(private val viewBinding: ItemSearchBinding) : RecyclerView.ViewHolder(viewBinding.root){
    fun bindData(searchResult: Search){
        viewBinding.itemSearchTvCity.text = searchResult.city
        viewBinding.itemSearchTvTime.text = searchResult.time
        viewBinding.itemSearchTvTemperature.text = searchResult.temp
        viewBinding.itemSearchTvWeather.text = searchResult.weather
        viewBinding.itemSearchTvHl.text = searchResult.highLowTemp
    }
}