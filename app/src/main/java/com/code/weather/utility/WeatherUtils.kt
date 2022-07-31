package com.code.weather.utility

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class WeatherUtils {
    companion object{
        private const val eDir = "East"
        private const val wDir = "West"
        private const val nDir = "North"
        private const val sDir = "South"

        fun windDirection(windDir: Int?): String =
            when {
                (windDir in 45..134) -> eDir
                (windDir in 135..224) -> sDir
                (windDir in 225..314) -> wDir
                else -> nDir
            }

        fun timeFormat(date: Date): String {
            val timeFormat: DateFormat =
                SimpleDateFormat("hh:mm a      MM-dd-yyyy", Locale.US)

            return timeFormat.format(date)
        }
    }
}