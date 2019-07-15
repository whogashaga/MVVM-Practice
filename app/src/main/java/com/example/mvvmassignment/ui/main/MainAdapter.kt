package com.example.mvvmassignment.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmassignment.R
import com.example.mvvmassignment.data.Results

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var mResultsList = ArrayList<Results>()
    var callback: OnZooInfoClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hall_info, parent, false)
        )
    }

    override fun getItemCount(): Int = mResultsList.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(mResultsList[position])
        callback?.onInfoClick()
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
            memo.text = filterString(results)

            itemLayout.setOnClickListener { v ->
                val action =
                    MainFragmentDirections.actionMainFragmentToDetailFragment(results ?: Results())
                action.title = results?.E_Name ?: ""
                Navigation.findNavController(v).navigate(action)
            }
        }
    }

    fun filterString(results: Results?): String {
        return if ("" == results?.E_Memo) "無休館資訊" else results?.E_Memo.toString()
    }

    fun updateData(resultsList: List<Results>) {
        mResultsList.clear()
        resultsList.forEach { result ->
            mResultsList.add(result)
        }
        notifyDataSetChanged()
    }

    interface OnZooInfoClickListener {
        fun onInfoClick()
    }

}