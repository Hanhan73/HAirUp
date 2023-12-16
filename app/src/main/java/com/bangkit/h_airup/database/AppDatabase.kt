package com.bangkit.h_airup.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bangkit.h_airup.dao.ApiDao
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.utils.Converters

@Database(
    entities = [ApiData::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apiResponseDao(): ApiDao

    companion object{

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "api.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }


    }
}