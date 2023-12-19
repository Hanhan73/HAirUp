package com.bangkit.h_airup.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bangkit.h_airup.dao.ApiDao
import com.bangkit.h_airup.dao.UserDao
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.model.UserEntity
import com.bangkit.h_airup.utils.Converters

@Database(
    entities = [ApiData::class, UserEntity::class],  // Add UserEntity here
    version = 3, // Increment the version when you make changes to the schema
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apiResponseDao(): ApiDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "data.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}