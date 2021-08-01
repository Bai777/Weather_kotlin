package com.example.weather_kotlin.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weather_kotlin.R
import com.example.weather_kotlin.databinding.MainActivityBinding
import com.example.weather_kotlin.ui.view.details.DetailsFragment
import com.example.weather_kotlin.ui.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)


        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}