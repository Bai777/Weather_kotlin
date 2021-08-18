package com.example.weather_kotlin.ui.view

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_kotlin.R
import com.example.weather_kotlin.databinding.MainActivityBinding
import com.example.weather_kotlin.exampleService.MainBroadcastReceiver
import com.example.weather_kotlin.exampleService.ThreadsFragment
import com.example.weather_kotlin.ui.view.history.HistoryFragment
import com.example.weather_kotlin.ui.view.main.MainFragment

class MainActivity : AppCompatActivity() {
    //создаём ресивер
    private val receiver = MainBroadcastReceiver()

    lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commit()
        }
        //регистрируем его через метод registerReceiver
        registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    override fun onDestroy() {
        //снимаем подписку на события
        unregisterReceiver(receiver)
        super.onDestroy()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_threads -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, ThreadsFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            R.id.menu_history -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, HistoryFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}