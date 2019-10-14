package com.example.mvvmassignment.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmassignment.LoadApiStatus
import com.example.mvvmassignment.data.AnimalResults
import com.example.mvvmassignment.data.SealedResult
import com.example.mvvmassignment.database.ZooRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val zooRepository: ZooRepository) : ViewModel() {

    val _items = MutableLiveData<List<AnimalResults?>>()

//    val items = zooRepository.getZooInfoLiveData()
    val items: LiveData<List<AnimalResults?>>
        get() = _items

    fun setAnimalRepo() = zooRepository.setAnimalData()

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun filterString(animalResults: AnimalResults?): String {
        return if ("" == animalResults?.E_Memo) "無休館資訊" else animalResults?.E_Memo.toString()
    }

    init {
        getZooInfoProperty()
    }

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private fun getZooInfoProperty() {

        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING

            val result = zooRepository.getZooInfoCoroutines()
            coroutineScope.launch {
                _items.value = when (result) {
                    is SealedResult.Success -> {
                        _status.value = LoadApiStatus.DONE
                        result.data
                    }
                    is SealedResult.Error -> {
                        _error.value = result.exception.toString()
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                    else -> {
                        _status.value = LoadApiStatus.ERROR
                        null
                    }
                }
            }
        }
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

