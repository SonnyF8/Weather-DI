package com.code.weather.repository.model

/* Detail view data */

data class DetailItem(
    val cityName: String? = null,
    val description: String? = null,
    val dateTime: String? = null,
    val temperature: String? = null,
    val humidity: String? = null,
    val pressure: String? = null,
    val clouds: String? = null,
    val visibility: String? = null,
    val windSpeed: String? = null
)
