package com.bangkit.h_airup.di

import com.bangkit.h_airup.data.AqiRepository

object Injection {
    fun provideRepository(): AqiRepository {
        return AqiRepository().getInstance()
    }
}