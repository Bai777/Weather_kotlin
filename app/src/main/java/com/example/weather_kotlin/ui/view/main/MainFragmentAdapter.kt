package com.example.weather_kotlin.ui.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_kotlin.R
import com.example.weather_kotlin.model.Weather

class MainFragmentAdapter (private var onItemViewClickListener: MainFragment.OnItemViewClickListener?): RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var weatherData: List<Weather> = listOf()

    fun setWeather(data: List<Weather>){
        weatherData = data
        notifyDataSetChanged()
    }
    fun removeListener() {
        onItemViewClickListener = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        return MainViewHolder(LayoutInflater.from(parent.context).inflate
            (R.layout.fragment_main_recycler_item, parent, false) as View)

    }

    override fun onBindViewHolder(
        holder: MainViewHolder, position: Int
    ) {
        holder.init(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun init(weather: Weather) {
            itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text =
                weather.city.city
            itemView.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(weather)
            }

        }
    }

}