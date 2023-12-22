package com.bangkit.h_airup.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bangkit.h_airup.response.ForecastResponse

@Entity(tableName = "forecast_responses")
data class ForecastData (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val forecastResponse: ForecastResponse?,
)
