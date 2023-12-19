package com.bangkit.h_airup.pref

data class UserModel(
    val userId: String = "",
    val name: String = "",
    val city: String = "",
    val province: String = "",
    val citygps: String = "",
    val provincegps: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val age: Int = 0,
    val sensitivity: String = "",
    val medHistory: String = "",
    val isFirstTime: Boolean = true,
    val isWorkManager: Boolean = false
)