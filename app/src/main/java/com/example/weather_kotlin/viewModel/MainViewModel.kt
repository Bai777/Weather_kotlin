package com.example.weather_kotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_kotlin.ui.view.AppState
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData())
: ViewModel() {
//    fun getData():LiveData<Any>{
//        getDataFromLocalSource()
//        return liveDataToObserve
//    }

    fun getLiveData() = liveDataToObserve
    fun getWeather() = getDataFromLocalSource()

    //метод getDataFromLocalSource, который имитирует запрос к БД
    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread(){
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(Any()))
        }.start()

    }
}