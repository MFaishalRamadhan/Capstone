package com.bangkitacademy.capstoneproject.loviso.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bangkitacademy.capstoneproject.loviso.R
import com.bangkitacademy.capstoneproject.loviso.data.response.RecommendationResponseItem
import com.squareup.picasso.Picasso

class HomeAdapter(private val context : Context, private val list: ArrayList<RecommendationResponseItem>): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_row_recommendation, parent, false)
        return ViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (image, address, distance, numRatings, name, rating, category, url, latitude, longitude) = list[position]
        val imageItem = Picasso.get().load(image).get()
        holder.imgItem.setImageBitmap(imageItem)
        holder.tvName.text = name
        holder.tvRating.text = rating.toString()
        holder.tvDistance.text = distance
        holder.cvHomeRecommendation.setOnClickListener{
            Toast.makeText(context, "Under Development", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendDataPredicts(data : ArrayList<RecommendationResponseItem>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    fun sendDataCollab(data : ArrayList<RecommendationResponseItem>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    /*class ViewHolder(var itemBinding: FragmentHomeBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(place: Bitmap){
            itemBinding.placeRecommendationList
        }
    }*/

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imgItem : ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName : TextView = itemView.findViewById(R.id.tv_item_name)
        val tvRating : TextView = itemView.findViewById(R.id.tv_rating)
        val tvDistance : TextView = itemView.findViewById(R.id.tv_range)
        val cvHomeRecommendation : CardView = itemView.findViewById(R.id.cv_home_recommendation)
    }

}