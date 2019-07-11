package com.example.mvvmassignment.utils

import com.example.mvvmassignment.database.ZooDatabase
import com.example.mvvmassignment.database.ZooRepository
import com.example.mvvmassignment.ui.main.MainViewModelFactory

object InjectUtils {

    fun provideMainViewModelFactory() :  MainViewModelFactory {
        val zooRepository = ZooRepository.getInstance(ZooDatabase.getInstance().zooDao)
        return MainViewModelFactory(zooRepository)
    }
}