package com.code.weather.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.code.weather.NetworkData
import com.code.weather.adapter.CurrentAdapter
import com.code.weather.adapter.HourlyAdapter
import com.code.weather.databinding.FragmentMainBinding
import com.code.weather.repository.CityDatabase
import com.code.weather.repository.CityLocation
import com.code.weather.repository.model.Current
import com.code.weather.repository.model.Hourly
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cityDatabase: CityDatabase,
    private val networkData: NetworkData
    ): ViewModel(), CoroutineScope {

    companion object {
        private const val CURRENT_WEATHER: Int = 0
        private const val HOURLY_WEATHER: Int = 1
    }

    private val currentResult: ArrayList<Current> = arrayListOf()
    private val hourlyResult: ArrayList<Hourly> = arrayListOf()

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

    fun loadWeatherData(
        actContext: WeakReference<Context>,
        binding: FragmentMainBinding,
        callType: Int
    ) {
        launch {
            withContext(Dispatchers.IO) {
                getAllCities()
            }.let { cities ->
                when (callType) {
                    CURRENT_WEATHER -> {
                        if (currentResult.isEmpty()) {
                            loadCurrentData(actContext, binding, cities)
                        } else {
                            binding.recycler.adapter =
                                CurrentAdapter(actContext, currentResult.sortedBy { it.name }
                                )
                        }
                    }
                    HOURLY_WEATHER -> {
                        clearWeatherResults()
                        loadHourlyData(binding, cities)
                    }
                }
            }
        }
    }

    private suspend fun loadCurrentData(
        actContext: WeakReference<Context>,
        binding: FragmentMainBinding,
        cityList: List<CityLocation>) {

        coroutineScope {
            cityList.map { city ->
                launch {
                    showProgress(binding)
                    currentResult.add(currentDataCall(city)!!)

                    binding.recycler.adapter =
                        CurrentAdapter(actContext, currentResult.sortedBy { it.name })

                    hideProgress(binding)
                    Log.d("Coroutine Result -->", "$currentResult")
                }
            }
        }
    }

    private suspend fun loadHourlyData(
        binding: FragmentMainBinding, cityList: List<CityLocation>) {

        coroutineScope {
            cityList.map { city ->
                launch {
                    showProgress(binding)
                    hourlyResult.add(hourlyDataCall(city)!!)

                    binding.recycler.adapter =
                        HourlyAdapter(hourlyResult.sortedBy { it.city?.name })

                    hideProgress(binding)
                    Log.d("Coroutine Result -->", "$hourlyResult")
                }
            }
        }
    }

    private suspend fun getAllCities(): List<CityLocation> =
        cityDatabase.cityDao().getAllCities()

    private suspend fun currentDataCall(cityLocation: CityLocation): Current? =
        withContext(Dispatchers.IO) {
            networkData.getCurrentWeatherData(cityLocation.latitude, cityLocation.longitude)
        }

    private suspend fun hourlyDataCall(cityLocation: CityLocation): Hourly? =
        withContext(Dispatchers.IO) {
            networkData.getHourlyWeatherData(cityLocation.latitude, cityLocation.longitude)
        }

    fun clearWeatherResults() {
        hourlyResult.clear()
        currentResult.clear()
    }

    private fun showProgress(binding: FragmentMainBinding) {
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideProgress(binding: FragmentMainBinding) {
        binding.progress.visibility = View.GONE
    }
}