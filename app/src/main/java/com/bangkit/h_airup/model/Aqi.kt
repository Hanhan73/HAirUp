package com.bangkit.h_airup.model

data class Aqi(
    val date: String,
    val status: String,
    val aqiLvl: Int,
    val weather: String
)