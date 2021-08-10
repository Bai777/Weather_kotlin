package com.example.weather_kotlin.exampleService

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weather_kotlin.R
import com.example.weather_kotlin.databinding.FragmentThreadsBinding
import com.example.weather_kotlin.exampleService.MainService.Companion.MAIN_SERVICE_STRING_EXTRA

class ThreadsFragment: Fragment() {

    companion object {
        fun newInstance() = ThreadsFragment()
        const val TEST_BROADCAST_INTENT_FILTER = "TEST BROADCAST INTENT FILTER"
        const val THREADS_FRAGMENT_BROADCAST_EXTRA = "THREADS_FRAGMENT_EXTRA"
        const val MAIN_SERVICE_INT_EXTRA = "MAIN_SERVICE_INT_EXTRA"
    }


    private var _binding: FragmentThreadsBinding? = null
    private val binding get() = _binding!!

    //Создаём свой BroadcastReceiver (получатель широковещательного сообщения)
    private val testReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
    //Достаём данные из интента
            intent.getStringExtra(THREADS_FRAGMENT_BROADCAST_EXTRA)?.let {
                addView(context.it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.registerReceiver(testReceiver, IntentFilter(TEST_BROADCAST_INTENT_FILTER))
    }

    override fun onDestroy() {
        context?.unregisterReceiver(testReceiver)
        super.onDestroy()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initServiceButton()
    }

    private fun initServiceButton() {
        binding.serviceButton.setOnClickListener{
            context?.let{
                it.startService(Intent(it, MainService::class.java).apply {

                    putExtra(
                        MAIN_SERVICE_INT_EXTRA,
                        binding.editText.text.toString().toInt()
                    )
                })
            }
        }
    }


}