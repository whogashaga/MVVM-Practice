package com.example.mvvmassignment.retrofit.client

import com.example.mvvmassignment.constant.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var ourInstance : Retrofit?= null

    val instance: Retrofit
        get() {
            if (ourInstance == null)
            {
                ourInstance = Retrofit.Builder()
//                    .client(getOkHttpClient())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return ourInstance!!
        }



}