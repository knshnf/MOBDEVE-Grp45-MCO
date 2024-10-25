package com.mobdeve_group45_mco.searchResults

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.AddLocationFragment
import com.mobdeve_group45_mco.databinding.ItemSearchBinding


class SearchViewHolder(
    private val viewBinding: ItemSearchBinding,
    private val fragmentManager: FragmentManager
)  : RecyclerView.ViewHolder(viewBinding.root){
    fun bindData(searchResult: Search){
        viewBinding.itemSearchTvCity.text = searchResult.city
        viewBinding.itemSearchTvTime.text = searchResult.time
        viewBinding.itemSearchTvTemperature.text = searchResult.temp
        viewBinding.itemSearchTvWeather.text = searchResult.weather
        viewBinding.itemSearchTvHl.text = searchResult.highLowTemp

        viewBinding.root.setOnClickListener {
                val bottomSheet: AddLocationFragment =
                    AddLocationFragment()
                bottomSheet.show(
                    fragmentManager,
                    "ModalBottomSheet")
            }
        }
    }
