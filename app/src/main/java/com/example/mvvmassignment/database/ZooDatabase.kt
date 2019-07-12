package com.example.mvvmassignment.database

import com.example.mvvmassignment.database.remote.ZooInfoRemote

class ZooDatabase private constructor() {

    var zooDao = ZooInfoRemote()
        private set


    companion object {
        @Volatile
        private var instance: ZooDatabase? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ZooDatabase().also { instance = it }
            }
    }
}