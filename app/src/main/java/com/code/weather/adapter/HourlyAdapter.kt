package com.code.weather.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.code.weather.R
import com.code.weather.databinding.HourlyRowBinding
import com.code.weather.repository.model.Hourly
import com.code.weather.utility.WeatherUtils
import java.util.*

class HourlyAdapter(val hourlyData: List<Hourly?>):
    RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    private lateinit var context: Context

    class HourlyViewHolder(val binding: HourlyRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        context = parent.context

        return HourlyViewHolder(
            HourlyRowBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return hourlyData.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        var hourlyList = ""

        for (idx in 0..7) {
            hourlyData.get(position)?.list?.get(idx).let { listItem ->
                hourlyList += "${listItem?.main?.temp!!.toInt()} \u2109        " +
                        WeatherUtils.timeFormat(Date(listItem.dt!! * 1000L)) + "\n"
            }
        }

        holder.binding.city.text = hourlyData.get(position)?.city?.name
        holder.binding.hourlyTemp.text = hourlyList.dropLast(1)

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(context.resources.getColor(R.color.quantum_grey300))
        } else {
            holder.itemView.setBackgroundColor(context.resources.getColor(R.color.quantum_grey400))
        }
    }
}
