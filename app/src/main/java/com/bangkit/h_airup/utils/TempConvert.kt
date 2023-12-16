package com.bangkit.h_airup.utils

object TempConvert {
    fun KelvinToCelsius(temp: Double): Int {
        return (temp - 273.15).toInt()
    }
}