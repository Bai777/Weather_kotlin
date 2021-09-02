package com.example.weather_kotlin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_kotlin.app.App.Companion.getHistoryDao
import com.example.weather_kotlin.repository.LocalRepository
import com.example.weather_kotlin.repository.LocalRepositoryImpl

class HistoryViewModel(
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: LocalRepositoryImpl = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {
//метод для получения данных из базы
    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        historyLiveData.value = AppState.Success(historyRepository.getAllHistory())
    }

    // метод удаления данных по названию города
    fun deleteByName(name:String){
        historyLiveData.value = AppState.Loading
        historyRepository.deleteEntityByName(name)
        historyLiveData.value = AppState.Success(historyRepository.getAllHistory())
    }

}
