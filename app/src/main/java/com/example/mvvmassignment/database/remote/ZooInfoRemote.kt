package com.example.mvvmassignment.database.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmassignment.constant.Constants.Companion.TAG
import com.example.mvvmassignment.data.AnimalResults
import com.example.mvvmassignment.retrofit.client.RetrofitClient
import com.example.mvvmassignment.retrofit.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ZooInfoRemote {

    private val resultsList = mutableListOf<AnimalResults>()
    private val resultsLiveData = MutableLiveData<List<AnimalResults>>()

    private val apiService: ApiService = RetrofitClient.instance.create(ApiService::class.java)
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        resultsLiveData.value = resultsList
    }

    private fun addInfo(animalResults: AnimalResults) {
        resultsList.add(animalResults)
        resultsLiveData.value = resultsList
    }

    fun getResults(): LiveData<List<AnimalResults>> {
        setAnimalInfo()
        return resultsLiveData
    }

    fun setAnimalInfo() {
        apiService.getZooInfo().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { zooInfo ->
                    zooInfo.result?.results?.forEach { results ->
                        addInfo(results ?: AnimalResults())
                        Log.d(TAG, "name = " + results?.E_Name)
                    }
                    Log.w(TAG, "list = " + (resultsLiveData.value))
                }
                , { Log.d(TAG, "error = $it") }
                , { Log.d(TAG, "load data onComplete!") })
            .let { compositeDisposable.add(it) }
    }
}