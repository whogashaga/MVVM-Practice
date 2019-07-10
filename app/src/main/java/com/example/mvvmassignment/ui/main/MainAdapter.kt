package com.example.mvvmassignment.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmassignment.R
import com.example.mvvmassignment.data.Results
import com.example.mvvmassignment.data.ZooInfo

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var mZooInfo = ZooInfo()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hall_info, parent, false)
        )
    }

    override fun getItemCount(): Int = (mZooInfo.result?.results?.size) ?: 0

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(mZooInfo.result?.results?.get(position))
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemLayout: View = itemView.findViewById(R.id.layout_item_main)
        var picture: ImageView = itemView.findViewById(R.id.image_hall_pic)
        var name: TextView = itemView.findViewById(R.id.text_hall_name)
        var information: TextView = itemView.findViewById(R.id.text_hall_info)
        var memo: TextView = itemView.findViewById(R.id.text_hall_memo)

        fun bind(results: Results?) {
            Glide.with(itemView).load(results?.E_Pic_URL).into(picture)
            name.text = results?.E_Name
            information.text = results?.E_Info
//            memo.text = results?.E_Memo
            memo.apply {
                text = if ("".equals(results?.E_Memo)) "無休館資訊" else results?.E_Memo
            }
        }
    }

    fun updateData(zooInfo: ZooInfo) {
        mZooInfo = zooInfo
        notifyDataSetChanged()
    }

}