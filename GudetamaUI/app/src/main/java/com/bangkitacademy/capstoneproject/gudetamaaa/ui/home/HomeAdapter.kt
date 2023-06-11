package com.bangkitacademy.capstoneproject.gudetamaaa.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkitacademy.capstoneproject.gudetamaaa.databinding.FragmentHomeBinding

class HomeAdapter(private val list: ArrayList<Home>, private val listener : (Home, Int) -> Unit): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val v = FragmentHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
        holder.itemView.setOnClickListener{ listener(list[position], position)}
    }

    class ViewHolder(var itemBinding: FragmentHomeBinding):
            RecyclerView.ViewHolder(itemBinding.root){
                fun bindItem(place: Home){

                }
            }

}