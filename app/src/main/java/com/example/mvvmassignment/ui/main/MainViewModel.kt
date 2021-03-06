package com.example.mvvmassignment.ui.main

import androidx.lifecycle.ViewModel
import com.example.mvvmassignment.data.AnimalResults
import com.example.mvvmassignment.database.ZooRepository

class MainViewModel(private val zooRepository: ZooRepository) : ViewModel() {

    val items = zooRepository.getZooInfoLiveData()
//    fun getZooRepo() = zooRepository.getZooInfoLiveData()

    fun setAnimalRepo() = zooRepository.setAnimalData()

    fun filterString(animalResults: AnimalResults?): String {
        return if ("" == animalResults?.E_Memo) "無休館資訊" else animalResults?.E_Memo.toString()
    }

    fun onClickListItem(result: AnimalResults?, callback: (Any) -> Unit) {
        val action =
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                result ?: AnimalResults()
            )
        action.title = result?.E_Name ?: ""
        callback(action)
    }

}

