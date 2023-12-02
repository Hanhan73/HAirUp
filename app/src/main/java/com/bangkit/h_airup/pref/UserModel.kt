package com.bangkit.h_airup.pref

data class UserModel(
    val name: String = "",
    val location: String = "",
    val age: Int = 0,
    val sensitivity: String = "",
    val medHistory: String = "",
    val isFirstTime: Boolean = true
)