package com.mobdeve_group45_mco.post

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.databinding.ItemPostBinding
import com.mobdeve_group45_mco.post.Post

class PostViewHolder(private val viewBinding: ItemPostBinding) : RecyclerView.ViewHolder(viewBinding.root){
    fun bindData(post: Post){
        this.viewBinding.itemPostTvName.text = post.displayName
//        this.viewBinding.itemPostTvUsername.text = "@" + post.userHandle
        this.viewBinding.itemPostTvUsername.text = ""
        this.viewBinding.itemPostTvPost.text = post.caption
        this.viewBinding.itemPostTvDate.text = post.createdAt.toStringNoYear()
        this.viewBinding.itemPostIvImage.setImageResource(post.imageId)
        this.viewBinding.itemPostTvLikes.text = post.likes.toString() + " Likes"
    }
}