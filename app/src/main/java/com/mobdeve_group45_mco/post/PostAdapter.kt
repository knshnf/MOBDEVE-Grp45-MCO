package com.mobdeve_group45_mco.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve_group45_mco.Post
import com.mobdeve_group45_mco.databinding.ItemPostBinding

class PostAdapter(private val posts: ArrayList<Post>): RecyclerView.Adapter<PostViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val itemViewBinding: ItemPostBinding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return PostViewHolder(itemViewBinding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindData(posts[position])
    }
}
