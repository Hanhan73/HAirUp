package com.bangkit.h_airup.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.h_airup.model.ApiData

@Dao
interface ApiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAPIResponse(apiData: ApiData)

    @Query("SELECT * FROM api_responses LIMIT 1")
    suspend fun getLatestAPIResponse(): ApiData?
}