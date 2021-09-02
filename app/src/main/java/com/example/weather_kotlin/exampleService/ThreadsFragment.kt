package com.example.weather_kotlin.exampleService

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weather_kotlin.databinding.FragmentThreadsBinding

class ThreadsFragment : Fragment() {

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
                binding.mainContainer.addView(TextView(context).apply {
                    text = it
                    textSize = 30f
                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(testReceiver, IntentFilter(TEST_BROADCAST_INTENT_FILTER))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        context?.let{
            LocalBroadcastManager.getInstance(it).unregisterReceiver(testReceiver)
        }

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
        binding.serviceButton.setOnClickListener {
            context?.let {
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