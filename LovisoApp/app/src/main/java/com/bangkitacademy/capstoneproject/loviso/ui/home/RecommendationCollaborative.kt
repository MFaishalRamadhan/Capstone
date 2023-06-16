package com.bangkitacademy.capstoneproject.loviso.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkitacademy.capstoneproject.loviso.data.response.RecommendationResponseItem
import com.bangkitacademy.capstoneproject.loviso.databinding.ItemRowRecommendationBinding
import com.squareup.picasso.Picasso

class RecommendationCollaborative: ListAdapter<RecommendationResponseItem, RecommendationCollaborative.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRowRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item  = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(val binding: ItemRowRecommendationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item : RecommendationResponseItem){
            binding.tvItemName.text = item.name
            binding.tvRange.text = item.distance
            binding.tvRating.text = item.rating.toString()

            val imageURL = item.image
            Picasso.get().load(imageURL).into(binding.imgItemPhoto)
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecommendationResponseItem>(){
            override fun areItemsTheSame(
                oldItem: RecommendationResponseItem,
                newItem: RecommendationResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RecommendationResponseItem,
                newItem: RecommendationResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}