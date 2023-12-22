package com.bangkit.h_airup.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.model.ForecastData
import com.bangkit.h_airup.model.UserEntity
import com.bangkit.h_airup.model.WeatherData

@Dao
interface ApiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAPIResponse(apiData: ApiData)

    @Query("SELECT * FROM api_responses ORDER BY id DESC LIMIT 1")
    suspend fun getLatestAPIResponse(): ApiData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherResponse(weatherData: WeatherData)

    @Query("SELECT * FROM weather_responses ORDER BY id DESC LIMIT 1")
    suspend fun getLatestWeatherResponse(): WeatherData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveForecastResponse(forecastData: ForecastData)

    @Query("SELECT * FROM weather_responses ORDER BY id DESC LIMIT 1")
    suspend fun getLatestForecastResponse(): ForecastData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_table LIMIT 1")
    suspend fun getUser(): UserEntity?
}