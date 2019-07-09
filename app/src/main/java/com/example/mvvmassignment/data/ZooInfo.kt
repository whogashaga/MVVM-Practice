package com.example.mvvmassignment.data

data class ZooInfo(
    val count: Int? = 0,
    val limit: Int? = 0,
    val offset: Int? = 0,
    val results: List<Category?>? = listOf(),
    val sort: String? = ""
)