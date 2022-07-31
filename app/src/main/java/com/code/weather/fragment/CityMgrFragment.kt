package com.code.weather.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.code.weather.R
import com.code.weather.databinding.FragmentCityMgrBinding
import com.code.weather.viewmodel.CityMgrViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityMgrFragment(): Fragment() {
    private val apiKey = "Google Places API key here"

    private lateinit var viewBinding: FragmentCityMgrBinding

    private val cityMgrViewModel by viewModels<CityMgrViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentCityMgrBinding.inflate(layoutInflater)

        return viewBinding.cityMgrFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.closeCityMgr.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_layout, MainFragment()).commit()
        }

        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    deleteCity(viewBinding, viewHolder.adapterPosition)
                }
            }
        ).attachToRecyclerView(viewBinding.cityRecycler)

        cityMgrViewModel.loadCityData(viewBinding)
        initPlaces(childFragmentManager.fragments.first() as AutocompleteSupportFragment)
    }

    private fun initPlaces(autoPlacesFragment: AutocompleteSupportFragment) {
        if (!Places.isInitialized())
            Places.initialize(requireContext(), apiKey)

        autoPlacesFragment.setPlaceFields(
            listOf(Place.Field.NAME, Place.Field.LAT_LNG)
        )

        autoPlacesFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                addCity(
                    viewBinding,
                    place.name!!,
                    place.latLng?.latitude!!,
                    place.latLng?.longitude!!
                )
            }

            override fun onError(status: Status) {
                Log.d("Places:", "Error")
            }
        })
    }

    private fun addCity(
        binding: FragmentCityMgrBinding,
        cityName: String, latitude: Double, longitude: Double) =
            cityMgrViewModel.addCity(
                binding, cityName, latitude, longitude
            )

    private fun deleteCity(
        binding: FragmentCityMgrBinding, cityListId: Int) =
            cityMgrViewModel.deleteCity(
                binding, cityListId
            )

}