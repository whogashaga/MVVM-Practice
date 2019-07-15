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
import com.example.mvvmassignment.data.AnimalResults

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var mResultsList = ArrayList<AnimalResults>()
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

        fun bind(animalResults: AnimalResults?) {
            Glide.with(itemView).load(animalResults?.E_Pic_URL).into(picture)
            name.text = animalResults?.E_Name
            information.text = animalResults?.E_Info
            memo.text = filterString(animalResults)

            itemLayout.setOnClickListener { v ->
                val action =
                    MainFragmentDirections.actionMainFragmentToDetailFragment(animalResults ?: AnimalResults())
                action.title = animalResults?.E_Name ?: ""
                Navigation.findNavController(v).navigate(action)
            }
        }
    }

    fun filterString(animalResults: AnimalResults?): String {
        return if ("" == animalResults?.E_Memo) "無休館資訊" else animalResults?.E_Memo.toString()
    }

    fun updateData(animalResultsList: List<AnimalResults>) {
        mResultsList.clear()
        animalResultsList.forEach { result ->
            mResultsList.add(result)
        }
        notifyDataSetChanged()
    }

    interface OnZooInfoClickListener {
        fun onInfoClick()
    }

}