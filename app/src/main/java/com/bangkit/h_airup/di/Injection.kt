package com.bangkit.h_airup.di

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.bangkit.h_airup.dao.ApiDao
import com.bangkit.h_airup.data.AqiRepository
import com.bangkit.h_airup.database.AppDatabase


object Injection {
    fun provideRepository(context: Context): AqiRepository {
        val apiDao = AppDatabase.getInstance(context).apiResponseDao()
        return AqiRepository.getInstance(apiDao)
    }
}