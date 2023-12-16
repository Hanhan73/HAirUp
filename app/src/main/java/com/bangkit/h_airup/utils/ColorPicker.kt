package com.bangkit.h_airup.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object ColorPicker {
    fun getAqiColor(aqiNumber: Int?): Color {
        return when {
            aqiNumber in 0..50 -> Color.Blue
            aqiNumber in 51..100 -> Color.Green
            aqiNumber in 101..150 -> Color.Yellow
            aqiNumber in 151..200 -> Color.Red
            else -> Color.Magenta
        }
    }
}