package com.example.mvvmassignment.data

data class ZooInformation(
    val count: Int? = 0,
    val limit: Int? = 0,
    val offset: Int? = 0,
    val results: List<Results?>? = listOf(),
    val sort: String? = ""
)