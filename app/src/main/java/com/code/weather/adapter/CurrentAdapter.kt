package com.code.weather.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.code.weather.R
import com.code.weather.databinding.CurrentRowBinding
import com.code.weather.fragment.DetailFragment
import com.code.weather.repository.model.Current
import com.code.weather.utility.WeatherUtils
import java.lang.ref.WeakReference
import kotlin.math.roundToInt

class CurrentAdapter(
    private val actContext: WeakReference<Context>,
    private val currentData: List<Current?>
    ): RecyclerView.Adapter<CurrentAdapter.CurrentViewHolder>() {

    private lateinit var context: Context

    class CurrentViewHolder(val binding: CurrentRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentViewHolder {
        context = parent.context

        return CurrentViewHolder(CurrentRowBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return currentData.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CurrentViewHolder, position: Int) {
        val cityName = currentData[position]?.name
        val temperature = currentData[position]?.main?.temp?.roundToInt()
        val clouds = currentData[position]?.clouds?.all!!
        val visibility = currentData[position]?.visibility.toString()
        val windSpeed = currentData[position]?.wind?.speed?.roundToInt()

        val windDirection =
            WeatherUtils.windDirection(currentData.get(position)?.wind?.deg!!)

        holder.binding.city.text = "$cityName"
        holder.binding.temp.text = "$temperature \u2109"
        holder.binding.clouds.text = "Cloud cover: $clouds%"
        holder.binding.visibility.text = "Visibility: $visibility Feet"
        holder.binding.winds.text = "Wind: $windSpeed mph $windDirection"

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(context.resources.getColor(R.color.quantum_grey300))
        } else {
            holder.itemView.setBackgroundColor(context.resources.getColor(R.color.quantum_grey400))
        }

        holder.itemView.setOnClickListener {
            (actContext.get() as AppCompatActivity)
                .supportFragmentManager.beginTransaction()
                .replace(
                    R.id.main_layout,
                    DetailFragment(currentData[position])
                ).addToBackStack(null).commit()
        }
    }
}
