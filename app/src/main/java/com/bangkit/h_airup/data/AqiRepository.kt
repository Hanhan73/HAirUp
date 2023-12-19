package com.bangkit.h_airup.data


import android.app.Application
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.bangkit.h_airup.dao.ApiDao
import com.bangkit.h_airup.database.AppDatabase
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.model.UserEntity
import com.bangkit.h_airup.response.APIResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

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

    suspend fun getUserId(): UserEntity? {
        return apiDao.getUser()
    }

}
