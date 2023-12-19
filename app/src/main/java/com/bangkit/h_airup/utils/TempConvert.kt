package com.bangkit.h_airup.utils

object TempConvert {
    fun KelvinToCelsius(temp: Double): Int {
        return (temp - 273.15).toInt()
    }
    fun KelvinToCelsiusn(temp: Double?): Int {
        var final = 0
        if (temp != null) {
            final = (temp - 273.15).toInt()
        }
        return final
    }
}