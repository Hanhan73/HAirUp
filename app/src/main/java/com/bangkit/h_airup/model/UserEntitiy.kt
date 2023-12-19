package com.bangkit.h_airup.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val nama: String,
    val umur: Int,
    val lokasi: String,
    val status: String? = null,
    val riwayatPenyakit: String? = null
)