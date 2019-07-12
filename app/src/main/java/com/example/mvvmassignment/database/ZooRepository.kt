package com.example.mvvmassignment.database

import com.example.mvvmassignment.database.remote.ZooInfoRemote

class ZooRepository constructor(private val zooInfoDao: ZooInfoRemote) {

    companion object {
        @Volatile
        private var instance: ZooRepository? = null

        fun getInstance(zooInfoDao: ZooInfoRemote) =
            instance ?: synchronized(this) {
                instance ?: ZooRepository(zooInfoDao).also { instance = it }
            }
    }

    fun getZooInfoLiveData()= zooInfoDao.getResults()

    fun setAnimalData() = zooInfoDao.setAnimalInfo()

}