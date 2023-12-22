package com.bangkit.h_airup.utils

object categoryAqi {

    fun getCategoryAqi(aqiNumber: Int?): String {
        return when {
            aqiNumber in 0..50 -> "Kualitas udara baik"
            aqiNumber in 51..100 -> "Kualitas Udara sedang"
            aqiNumber in 101..150 -> "Tidak sehat untuk masyarakat rentan"
            aqiNumber in 151..200 -> "Kualitas udara tidak sehat"
            aqiNumber in 201..300 -> "Kualitas udara sangat tidak sehat"
            else -> "Kualitas udara berbahaya"
        }
    }
}