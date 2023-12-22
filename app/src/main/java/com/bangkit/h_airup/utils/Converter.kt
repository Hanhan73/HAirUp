package com.bangkit.h_airup.utils

import androidx.room.TypeConverter
import com.bangkit.h_airup.response.APIResponse
import com.bangkit.h_airup.response.ForecastResponse
import com.bangkit.h_airup.response.WeathersResponse
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
    @TypeConverter
    fun fromWeatherResponse(weatherResponse: WeathersResponse?): String {
        return Gson().toJson(weatherResponse)
    }

    @TypeConverter
    fun toWeatherResponse(json: String): WeathersResponse? {
        return Gson().fromJson(json, WeathersResponse::class.java)
    }

    @TypeConverter
    fun fromForecastResponse(forecastResponse: ForecastResponse?): String {
        return Gson().toJson(forecastResponse)
    }

    @TypeConverter
    fun toForecastResponse(json: String): ForecastResponse? {
        return Gson().fromJson(json, ForecastResponse::class.java)
    }
}