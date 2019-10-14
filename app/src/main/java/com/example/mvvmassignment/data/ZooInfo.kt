package com.example.mvvmassignment.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ZooInfo(
    val result: Animal? = Animal()
) : Parcelable {

    fun getDataItems(): List<AnimalResults?> {
        val items = ArrayList<AnimalResults?>()
        for (innerResult in result?.results!!) {
            items.add(innerResult)
        }
        return items
    }
}