package com.example.weather_kotlin.exampleService

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MainService(name: String = "MainService") : IntentService(name) {
    companion object {
        private const val TAG = "MainServiceTAG"
        const val MAIN_SERVICE_STRING_EXTRA = "MainServiceExtra"
    }


    override fun onHandleIntent(intent: Intent?) {
        createLogMessage("onHandleIntent ${intent?.getStringExtra(MAIN_SERVICE_STRING_EXTRA)}")
    }

    override fun onCreate() {
        createLogMessage("onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createLogMessage("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        createLogMessage("onDestroy")
        super.onDestroy()
    }


    //Выводим уведомление в строке состояния
    private fun createLogMessage(s: String) {
        Log.d(TAG, s)
    }


}