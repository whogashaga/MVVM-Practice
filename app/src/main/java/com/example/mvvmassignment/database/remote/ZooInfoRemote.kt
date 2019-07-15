package com.example.mvvmassignment.database.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmassignment.constant.Constants
import com.example.mvvmassignment.data.Results
import com.example.mvvmassignment.retrofit.client.RetrofitClient
import com.example.mvvmassignment.retrofit.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ZooInfoRemote {

    private val resultsList = mutableListOf<Results>()
    private val resultsLiveData = MutableLiveData<List<Results>>()

    private val apiService: ApiService = RetrofitClient.instance.create(ApiService::class.java)
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        resultsLiveData.value = resultsList
    }

    private fun addInfo(results: Results) {
        resultsList.add(results)
        resultsLiveData.value = resultsList
    }

    fun getResults() = resultsLiveData as LiveData<List<Results>>

    fun setAnimalInfo() {
        apiService.getZooInfo().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { zooInfo ->
                    zooInfo.result?.results?.forEach { results ->
                        addInfo(results ?: Results())
                        Log.d(Constants.TAG, "name = " + results?.E_Name)
                    }
                    Log.w(Constants.TAG, "list = " + (resultsLiveData.value))
                }
                , { Log.d(Constants.TAG, "error = $it") }
                , { Log.d(Constants.TAG, "load data onComplete!") })
            .let { compositeDisposable.add(it) }
    }
}