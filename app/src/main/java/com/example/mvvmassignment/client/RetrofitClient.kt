package com.example.mvvmassignment.client

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://data.taipei/opendata/datalist"
    private var ourInstance : Retrofit?= null

    val instance: Retrofit
        get() {
            if (ourInstance == null)
            {
                ourInstance = Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return ourInstance!!
        }

    private fun getOkHttpClient() : OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor {
                    chain -> val request = chain.request()
                .newBuilder()
                .removeHeader("user-agent")
                .header("user-agent", "request")
                .build()
                chain.proceed(request)
            }.build()
    }
}