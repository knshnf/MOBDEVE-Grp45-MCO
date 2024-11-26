package com.mobdeve_group45_mco.post

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.Post
import com.mobdeve_group45_mco.databinding.ItemPostBinding

class PostViewHolder(private val viewBinding: ItemPostBinding) : RecyclerView.ViewHolder(viewBinding.root){
    fun bindData(post: Post){
        this.viewBinding.itemPostTvName.text = post.name
        this.viewBinding.itemPostTvPost.text = post.post
        this.viewBinding.itemPostTvDate.text = post.date
    }
}