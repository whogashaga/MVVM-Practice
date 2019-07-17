package com.example.mvvmassignment.ui.detail

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.mvvmassignment.data.AnimalResults
import com.google.android.material.snackbar.Snackbar

class DetailViewModel: ViewModel() {

    fun filterString(animalResults: AnimalResults?): String {
        return if ("" == animalResults?.E_Memo) "無休館資訊" else animalResults?.E_Memo.toString()
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