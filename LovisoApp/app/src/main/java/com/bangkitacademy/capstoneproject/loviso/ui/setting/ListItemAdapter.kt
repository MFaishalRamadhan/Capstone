package com.bangkitacademy.capstoneproject.loviso.ui.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkitacademy.capstoneproject.loviso.R

class ListItemAdapter(private val listItem: ArrayList<Setting>) : RecyclerView.Adapter<ListItemAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val iconSetting: ImageView = itemView.findViewById(R.id.account_icon)
        val nameSetting: TextView = itemView.findViewById(R.id.setting_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_account_setting_row, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (settingName, icon) = listItem[position]
        holder.iconSetting.setImageDrawable(icon)
        holder.nameSetting.text = settingName
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

}