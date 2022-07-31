package com.code.weather

import com.code.weather.repository.model.Current
import com.code.weather.repository.model.Hourly
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory.create
import retrofit2.http.GET
import retrofit2.http.Query

class NetworkData {
    private val apiOpenWeatherBaseUrl = "https://api.openweathermap.org/data/2.5/"
    private val apiKey = "OpenWeather API key here"

    suspend fun getCurrentWeatherData(latitude: Double, longitude: Double): Current? {
        val retrofit = Retrofit
            .Builder().baseUrl(apiOpenWeatherBaseUrl)
            .addConverterFactory(create())
            .build()

        return retrofit.create(
            CurrentWeatherDataEndpoint::class.java)
                .callWeatherData(apiKey, latitude, longitude).body()
    }

    suspend fun getHourlyWeatherData(latitude: Double, longitude: Double): Hourly? {
        val retrofit = Retrofit
            .Builder().baseUrl(apiOpenWeatherBaseUrl)
            .addConverterFactory(create())
            .build()

        return retrofit.create(
            HourlyWeatherDataEndpoint::class.java)
                .callWeatherData(apiKey, latitude, longitude).body()
    }
}

interface CurrentWeatherDataEndpoint {
    @GET("weather")
    suspend fun callWeatherData(
        @Query("appid") apiKey: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "imperial",
    ): Response<Current>
}

interface HourlyWeatherDataEndpoint {
    @GET("forecast")
    suspend fun callWeatherData(
        @Query("appid") apiKey: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "imperial",
    ): Response<Hourly>
}