package com.bangkit.h_airup.model

import java.io.File

data class RecommendRequestBody(
    val waktuKeluar: Int,
    val tanggal: Int,
    val lokasi: String?,

)