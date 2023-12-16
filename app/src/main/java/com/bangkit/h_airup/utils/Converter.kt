package com.bangkit.h_airup.utils

import androidx.room.TypeConverter
import com.bangkit.h_airup.response.APIResponse
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromAPIResponse(apiResponse: APIResponse?): String? {
        return Gson().toJson(apiResponse)
    }

    @TypeConverter
    fun toAPIResponse(json: String?): APIResponse? {
        return Gson().fromJson(json, APIResponse::class.java)
    }
}