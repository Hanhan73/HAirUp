package com.bangkit.h_airup.data


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class AqiRepository {



        @Volatile
        private var instance: AqiRepository? = null

        fun getInstance(): AqiRepository =
            instance ?: synchronized(this) {
                AqiRepository().apply {
                    instance = this
                }
            }

}