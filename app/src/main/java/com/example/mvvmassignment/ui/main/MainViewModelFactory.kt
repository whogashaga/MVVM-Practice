package com.example.mvvmassignment.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmassignment.database.ZooRepository

class MainViewModelFactory(private val zooRepository: ZooRepository)
        : ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(zooRepository) as T
        }
}