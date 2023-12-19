package com.bangkit.h_airup.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ConvertTime {
    fun convertToTime(unixTimestamp: Long?): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = Date(unixTimestamp?.times(1000) ?: 0) // Multiply by 1000 to convert seconds to milliseconds
        return dateFormat.format(date)
    }
}