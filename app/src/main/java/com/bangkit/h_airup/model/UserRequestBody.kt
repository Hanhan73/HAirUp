package com.bangkit.h_airup.model

data class UserRequestBody(
    val nama: String,
    val umur: Int,
    val lokasi: String,
    val status: String? = null,
    val riwayatPenyakit: String? = null
)