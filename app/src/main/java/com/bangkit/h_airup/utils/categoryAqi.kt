package com.bangkit.h_airup.utils

import androidx.compose.ui.graphics.Color

object categoryAqi {

    fun getCategoryAqi(aqiNumber: Int?): String {
        return when {
            aqiNumber in 0..50 -> "Kualitas Udara Sangat Baik"
            aqiNumber in 51..100 -> "Kualitas Udara Cukup Baik"
            aqiNumber in 101..150 -> "Kualitas Udara Buruk"
            aqiNumber in 151..200 -> "Kualitas Udara Sangat Buruk"
            else -> "Kualitas Udara Tidak Diketahui"
        }
    }
}