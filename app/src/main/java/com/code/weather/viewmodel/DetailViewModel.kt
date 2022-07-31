package com.code.weather.viewmodel

import androidx.lifecycle.ViewModel
import com.code.weather.repository.model.Current
import com.code.weather.repository.model.DetailItem
import com.code.weather.utility.WeatherUtils
import java.util.*
import kotlin.math.roundToInt

class DetailViewModel: ViewModel() {
    lateinit var detailItem: DetailItem

    fun getCityDetails(currentData: Current?): DetailItem {
        detailItem = DetailItem(
            cityName = currentData?.name!!,
            description = "Currently: ${currentData.weather?.first()?.description!!}",
            dateTime = WeatherUtils.timeFormat(Date(currentData.dt!! * 1000L)) + "\n",
            temperature = "Temperature is: ${currentData.main?.temp?.roundToInt()} \u2109",
            humidity = "Relative humidity: ${currentData.main?.humidity} %",
            pressure = "Barometer is at: ${currentData.main?.pressure} mmHg",
            clouds = "Cloud cover is at: ${currentData.clouds?.all}%",
            visibility = "Visibility ceiling: ${currentData.visibility} Feet",
            windSpeed = "Wind speed is: ${currentData.wind?.speed?.roundToInt()} Mph - " +
                    WeatherUtils.windDirection(currentData.wind?.deg)
        )
        return detailItem
    }
}