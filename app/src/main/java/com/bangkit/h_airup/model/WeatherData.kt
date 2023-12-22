package com.bangkit.h_airup.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bangkit.h_airup.response.WeathersResponse


@Entity(tableName = "weather_responses")
data class WeatherData (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val weatherResponse: WeathersResponse?,
)
