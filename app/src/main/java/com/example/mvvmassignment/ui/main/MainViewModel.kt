package com.example.mvvmassignment.ui.main

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.mvvmassignment.data.AnimalResults
import com.example.mvvmassignment.database.ZooRepository
import com.example.mvvmassignment.ui.detail.DetailFragmentDirections
import com.google.android.material.snackbar.Snackbar

class MainViewModel(private val zooRepository: ZooRepository) : ViewModel() {

    val items = zooRepository.getZooInfoLiveData()
//    fun getZooRepo() = zooRepository.getZooInfoLiveData()

    fun setAnimalRepo() = zooRepository.setAnimalData()

    fun filterString(animalResults: AnimalResults?): String {
        return if ("" == animalResults?.E_Memo) "無休館資訊" else animalResults?.E_Memo.toString()
    }

    fun onClickListItem(result: AnimalResults?, view: View) {
        val action =
            MainFragmentDirections.actionMainFragmentToDetailFragment(
                result ?: AnimalResults()
            )
        action.title = result?.E_Name ?: ""
        Navigation.findNavController(view).navigate(action)
    }

    fun onClickOpenWebView(result: AnimalResults?, view: View) {
        Snackbar.make(view, "目前瀏覽人數眾多 請耐心等候畫面", Snackbar.LENGTH_SHORT)
            .setAction("Action", null)
            .show()

        val action= DetailFragmentDirections.actionDetailFragmentToWebFragment()
        action.webUrl = result?.E_URL ?: ""
        action.argTitle = result?.E_Name ?: ""
        Navigation.findNavController(view).navigate(action)
    }

}

