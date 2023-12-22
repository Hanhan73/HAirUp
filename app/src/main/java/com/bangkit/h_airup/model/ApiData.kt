package com.bangkit.h_airup.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bangkit.h_airup.response.APIResponse


@Entity(tableName = "api_responses")
data class ApiData (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val response: APIResponse?,
)
