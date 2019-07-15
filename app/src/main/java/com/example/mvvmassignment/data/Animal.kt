package com.example.mvvmassignment.data

data class Animal(
    val count: Int? = 0,
    val limit: Int? = 0,
    val offset: Int? = 0,
    val results: List<AnimalResults?>? = listOf(),
    val sort: String? = ""
)