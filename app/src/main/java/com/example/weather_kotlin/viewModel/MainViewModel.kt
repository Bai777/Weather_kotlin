package com.example.weather_kotlin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_kotlin.repository.Repository
import com.example.weather_kotlin.repository.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve:
    MutableLiveData<Any> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()

) : ViewModel() {


    fun getLiveData() = liveDataToObserve
    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian = true)





    //метод getDataFromLocalSource, который имитирует запрос к БД
    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread() {
            sleep(2000)
            liveDataToObserve.postValue(AppState.Success(if (isRussian)repositoryImpl.getWeatherFromLocalStorageRus() else
                repositoryImpl.getWeatherFromLocalStorageWorld()))
        }.start()

    }
}