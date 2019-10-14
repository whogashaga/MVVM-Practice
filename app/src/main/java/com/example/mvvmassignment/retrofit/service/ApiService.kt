package com.example.mvvmassignment.retrofit.service

import com.example.mvvmassignment.constant.Constants.Companion.ANIMAL_PATH
import com.example.mvvmassignment.data.ZooInfo
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {

    @GET(ANIMAL_PATH)
    fun getZooInfo() : Observable<ZooInfo>

    @GET(ANIMAL_PATH)
    fun getZooInfoCoroutine() : Deferred<ZooInfo>
}