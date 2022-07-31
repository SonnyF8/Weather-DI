package com.code.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.code.weather.R
import com.code.weather.databinding.CityMgrRowBinding
import com.code.weather.repository.CityLocation

class CityMgrAdapter(val cityData: List<CityLocation?>):
    RecyclerView.Adapter<CityMgrAdapter.CityMgrViewHolder>() {

    private lateinit var context: Context

    class CityMgrViewHolder(val binding: CityMgrRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityMgrViewHolder {
        context = parent.context

        return CityMgrViewHolder(
            CityMgrRowBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return cityData.size
    }

    override fun onBindViewHolder(holder: CityMgrViewHolder, position: Int) {
        holder.binding.city.text = cityData.get(position)?.cityName

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(context.resources.getColor(R.color.quantum_grey300))
        } else {
            holder.itemView.setBackgroundColor(context.resources.getColor(R.color.quantum_grey400))
        }
    }
}
