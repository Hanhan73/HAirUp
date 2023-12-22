package com.bangkit.h_airup.utils

import androidx.compose.ui.graphics.Color

object ColorPicker {
    fun getAqiColor(aqiNumber: Int?): Color {
        return when {
            aqiNumber in 0..50 -> Color(red = 144, green = 238, blue = 144)
            aqiNumber in 51..100 -> Color(red = 255, green = 255, blue = 128)
            aqiNumber in 101..150 -> Color(red = 255, green = 165, blue = 0)
            aqiNumber in 151..200 -> Color(red = 255, green = 99, blue = 71)
            aqiNumber in 201..300 -> Color(red = 147, green = 112, blue = 219)
            else -> Color(red = 160, green = 82, blue = 45)
        }
    }
}