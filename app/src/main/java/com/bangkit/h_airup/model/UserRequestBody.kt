package com.bangkit.h_airup.model

import java.io.File

data class UserRequestBody(
    val nama: String,
    val umur: Int,
    val lokasi: String,
    val image: File?,
    val status: String? = null,
    val riwayatPenyakit: String? = null
)