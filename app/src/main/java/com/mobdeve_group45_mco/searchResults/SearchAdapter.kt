package com.mobdeve_group45_mco.searchResults

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.databinding.ItemSearchBinding

class SearchAdapter(private val searchResults: ArrayList<Search>): RecyclerView.Adapter<SearchViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        val itemViewBinding: ItemSearchBinding = ItemSearchBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return SearchViewHolder(itemViewBinding)
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindData(searchResults[position])
    }
}
