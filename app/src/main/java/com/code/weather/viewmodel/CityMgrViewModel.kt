package com.code.weather.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.code.weather.adapter.CityMgrAdapter
import com.code.weather.databinding.FragmentCityMgrBinding
import com.code.weather.repository.CityDatabase
import com.code.weather.repository.CityLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class CityMgrViewModel @Inject constructor(
    private val cityDatabase: CityDatabase
)
    : ViewModel(), CoroutineScope {

    private lateinit var citiesList: List<CityLocation>

    private val job = Job()
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.d("Coroutine exception -->", "${exception.message}")
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + handler

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun loadCityData(binding: FragmentCityMgrBinding) {
        launch {
            withContext(Dispatchers.IO) {
                getAllCities()
            }.let { cities ->
                citiesList = cities
                binding.cityRecycler.adapter =
                    CityMgrAdapter(cities.sortedBy { cities.first().cityName })
            }
        }
    }

    private suspend fun getAllCities(): List<CityLocation> = cityDatabase.cityDao().getAllCities()

    fun addCity(
        binding: FragmentCityMgrBinding,
        cityName: String, latitude: Double, longitude: Double
    ) {
        launch {
            withContext(Dispatchers.IO) {
                cityDatabase.cityDao().insertCity(CityLocation(cityName, latitude, longitude))
                loadCityData(binding)
            }
        }
    }

    fun deleteCity(
        binding: FragmentCityMgrBinding, cityListId: Int
    ) {
        launch {
            withContext(Dispatchers.IO) {
                cityDatabase.cityDao().deleteCity(
                    CityLocation(
                        citiesList.get(cityListId).cityName,
                        citiesList.get(cityListId).latitude,
                        citiesList.get(cityListId).longitude
                    )
                )
                loadCityData(binding)
            }
        }
    }
}