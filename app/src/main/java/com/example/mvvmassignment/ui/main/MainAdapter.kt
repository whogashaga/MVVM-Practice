package com.example.mvvmassignment.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmassignment.R

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hall_info, parent, false)
        )
    }

    override fun getItemCount(): Int = 16

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind()
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemLayout: View = itemView.findViewById(R.id.layout_item_main)
        var picture: ImageView = itemView.findViewById(R.id.image_hall_pic)
        var name: TextView = itemView.findViewById(R.id.text_hall_name)
        var information: TextView = itemView.findViewById(R.id.text_hall_info)
        var memo: TextView = itemView.findViewById(R.id.text_hall_memo)

        fun bind() {

        }
    }

}