package com.code.weather.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.code.weather.databinding.FragmentDetailBinding
import com.code.weather.repository.model.Current
import com.code.weather.viewmodel.DetailViewModel

class DetailFragment(val currentData: Current?): Fragment() {
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var viewBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDetailBinding.inflate(layoutInflater)

        return viewBinding.detailFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        viewBinding.closeDetail.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val detailItem = detailViewModel.getCityDetails(currentData)

        viewBinding.city.text = detailItem.cityName
        viewBinding.description.text = detailItem.description
        viewBinding.dateTime.text = detailItem.dateTime
        viewBinding.temperature.text = detailItem.temperature
        viewBinding.humidity.text = detailItem.humidity
        viewBinding.pressure.text = detailItem.pressure
        viewBinding.clouds.text = detailItem.clouds
        viewBinding.visibility.text = detailItem.visibility
        viewBinding.windSpeed.text = detailItem.windSpeed
    }
}