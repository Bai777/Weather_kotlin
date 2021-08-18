package com.example.weather_kotlin.ui.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_kotlin.R
import com.example.weather_kotlin.model.Weather
import com.example.weather_kotlin.ui.view.history.HistoryAdapter.RecyclerItemViewHolder
import kotlinx.android.synthetic.main.fragment_history_item.view.*

class HistoryAdapter() : RecyclerView.Adapter<RecyclerItemViewHolder>() {

    private var data: List<Weather> = arrayListOf()
    private lateinit var listener: OnClickAdapterItem

    fun setData(data: List<Weather>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_history_item, parent, false) as View
        )
    }

    fun setListener(listener: OnClickAdapterItem) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Weather) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.recyclerViewItem.text =
                    String.format("%s %d %s", data.city.name, data.temperature, data.condition)
                itemView.setOnClickListener {
//                    getHistoryDao().deleteFromAllCityName(data.city.name)

                    listener.onItemClick(data.city.name, adapterPosition)
                    Toast.makeText(
                        itemView.context,
                        "on click: ${data.city.name}",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                }
            }
        }

    }

}