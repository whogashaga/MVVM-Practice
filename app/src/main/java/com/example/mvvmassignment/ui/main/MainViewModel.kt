package com.example.mvvmassignment.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmassignment.data.ZooInfo
import com.example.mvvmassignment.retrofit.client.RetrofitClient

class MainViewModel : ViewModel() {

    private val mClient = RetrofitClient.instance



//    private val zooInfoLiveData = MutableLiveData<ZooInfo>()
//
//    fun getZooInfo() = zooInfoLiveData as LiveData<ZooInfo>
}
