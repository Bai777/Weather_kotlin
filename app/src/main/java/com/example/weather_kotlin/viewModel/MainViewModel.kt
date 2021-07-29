package com.example.weather_kotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData())
: ViewModel() {
    fun getData():LiveData<Any>{
        getDataFromLocalSource()
        return liveDataToObserve
    }

    //метод getDataFromLocalSource, который имитирует запрос к БД
    private fun getDataFromLocalSource() {
        Thread(){
            sleep(1000)
            liveDataToObserve.postValue(Any())
        }.start()

    }
}