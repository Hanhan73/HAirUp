package com.bangkit.h_airup.utils

import com.bangkit.h_airup.R


object IconPicker {
    fun getAqiIcon(aqiNumber: Int?): Int {
        return when {
            aqiNumber in 0..50 -> R.drawable.good
            aqiNumber in 51..100 -> R.drawable.moderate
            aqiNumber in 101..150 -> R.drawable.unhealthy_for_sensitive
            aqiNumber in 151..200 -> R.drawable.unhealthy
            aqiNumber in 201..300 -> R.drawable.very_unhealthy
            else -> R.drawable.hazardous
        }
    }

    fun getWeatherIcon(weather: String?): Int {
        return when {
            weather == "01d" || weather == "01n"-> R.drawable.sun
            weather == "02d" || weather == "02n" || weather == "03d" || weather == "04d"-> R.drawable.cloudy
            weather == "09d"-> R.drawable.rainy
            weather == "10d" || weather == "10n" -> R.drawable.cloudy_with_rain
            weather == "11d"-> R.drawable.storm
            else -> R.drawable.cloudy
        }
    }

    fun getWeatherIllustration(weather: String?): Int {
        return when {
            weather == "01d" || weather == "01n"-> R.drawable.sunny
            weather == "02d" || weather == "02n" || weather == "03d" || weather == "04d"-> R.drawable.sunny_cloud
            weather == "09d"-> R.drawable.rain_out
            weather == "10d" || weather == "10n" -> R.drawable.cloudy_with_rain
            weather == "11d"-> R.drawable.storm1
            else -> R.drawable.black_cloud
        }
    }
}