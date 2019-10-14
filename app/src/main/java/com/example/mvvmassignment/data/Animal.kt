package com.example.mvvmassignment.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Animal(
    val count: Int? = 0,
    val limit: Int? = 0,
    val offset: Int? = 0,
    val results: List<AnimalResults?>? = listOf(),
    val sort: String? = ""
) : Parcelable