package com.bangkit.h_airup.pref

data class UserModel(
    val name: String = "",
    val location: String = "",
    val province: String = "",
    val city: String = "",
    val provinces: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val age: Int = 0,
    val sensitivity: String = "",
    val medHistory: String = "",
    val isFirstTime: Boolean = true,
    val isWorkManager: Boolean = false
)