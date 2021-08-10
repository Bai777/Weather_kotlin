package com.example.weather_kotlin.exampleService

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weather_kotlin.R
import com.example.weather_kotlin.databinding.FragmentThreadsBinding
import com.example.weather_kotlin.exampleService.MainService.Companion.MAIN_SERVICE_STRING_EXTRA

class ThreadsFragment: Fragment() {


    private var _binding: FragmentThreadsBinding? = null
    private val binding get() = _binding!!

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
                        MAIN_SERVICE_STRING_EXTRA, getString(R.string.hello_from_thread_fragment)
                    )
                })
            }
        }
    }
    companion object {
        fun newInstance() = ThreadsFragment()
    }

}