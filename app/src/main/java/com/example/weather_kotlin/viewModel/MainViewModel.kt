package com.example.weather_kotlin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_kotlin.model.Repository
import com.example.weather_kotlin.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData())
: ViewModel() {
    private val repositoryImpl: Repository = RepositoryImpl()
//    fun getData():LiveData<Any>{
//        getDataFromLocalSource()
//        return liveDataToObserve
//    }

    fun getLiveData() = liveDataToObserve
    fun getWeatherFromLocalSource() = getDataFromLocalSource()
    fun getWeatherFromRemoteSource() = getDataFromLocalSource()
//    fun getWeather() = getDataFromLocalSource()

    //метод getDataFromLocalSource, который имитирует запрос к БД
    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread(){
            sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getWeatherFromLocalStorage()))
        }.start()

    }
}