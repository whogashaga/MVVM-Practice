package com.example.mvvmassignment.ui.main

import androidx.lifecycle.ViewModel
import com.example.mvvmassignment.database.ZooRepository

class MainViewModel(private val zooRepository: ZooRepository) : ViewModel() {

    fun getZooRepo() = zooRepository.getZooInfoLiveData()

    fun setAnimalRepo() = zooRepository.setAnimalData()
}

