package com.bangkit.h_airup.data


import com.bangkit.h_airup.dao.ApiDao
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.model.ForecastData
import com.bangkit.h_airup.model.UserEntity
import com.bangkit.h_airup.model.WeatherData


class AqiRepository(private val apiDao: ApiDao) {

    companion object {
        @Volatile
        private var instance: AqiRepository? = null

        fun getInstance(apiDao: ApiDao): AqiRepository =
            instance ?: synchronized(this) {
                AqiRepository(apiDao).apply {
                    instance = this
                }
            }
    }

    suspend fun saveAPIResponse(apiData: ApiData) {
        apiDao.saveAPIResponse(apiData)
    }

    suspend fun getLatestAPIResponse(): ApiData? {
        return apiDao.getLatestAPIResponse()
    }
    suspend fun getLatestWeatherResponse(): WeatherData? {
        return apiDao.getLatestWeatherResponse()
    }

    suspend fun getLatestForecastResponse(): ForecastData? {
        return apiDao.getLatestForecastResponse()
    }

    suspend fun getUserId(): UserEntity? {
        return apiDao.getUser()
    }

}
