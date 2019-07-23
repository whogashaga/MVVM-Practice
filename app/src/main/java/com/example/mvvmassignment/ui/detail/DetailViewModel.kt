package com.example.mvvmassignment.ui.detail

import androidx.lifecycle.ViewModel
import com.example.mvvmassignment.data.AnimalResults

class DetailViewModel : ViewModel() {

    fun filterString(animalResults: AnimalResults?): String {
        return if ("" == animalResults?.E_Memo) "無休館資訊" else animalResults?.E_Memo.toString()
    }

    fun onClickOpenWebView(result: AnimalResults?, callback: (Any) -> Unit) {
        val action= DetailFragmentDirections.actionDetailFragmentToWebFragment()
        action.webUrl = result?.E_URL ?: ""
        action.argTitle = result?.E_Name ?: ""
        callback(action)
    }
}