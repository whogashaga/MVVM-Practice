package com.example.mvvmassignment.service

import com.example.mvvmassignment.data.ZooBean
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET("/apiAccess?scope=resourceAquire&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
    fun getUserPath() : Observable<ZooBean>
}