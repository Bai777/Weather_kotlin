package com.example.weather_kotlin.exampleService

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weather_kotlin.exampleService.ThreadsFragment.Companion.TEST_BROADCAST_INTENT_FILTER
import com.example.weather_kotlin.exampleService.ThreadsFragment.Companion.THREADS_FRAGMENT_BROADCAST_EXTRA

class MainService(name: String = "MainService") : IntentService(name) {
    companion object {
        private const val TAG = "MainServiceTAG"
        const val MAIN_SERVICE_INT_EXTRA = "MainServiceExtraInt"
    }


    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            sendBack(it.getIntExtra(MAIN_SERVICE_INT_EXTRA, 0).toString())
        }

    }

    //Отправка уведомления о завершении сервиса
    private fun sendBack(result: String) {
        val broadcastIntent = Intent(TEST_BROADCAST_INTENT_FILTER)
        broadcastIntent.putExtra(THREADS_FRAGMENT_BROADCAST_EXTRA, result)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
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