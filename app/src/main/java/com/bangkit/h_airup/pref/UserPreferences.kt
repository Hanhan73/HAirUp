package com.bangkit.h_airup.pref

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>, private val context: Context) {

    suspend fun saveUserData(name: String, location: String, age: Int) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[LOCATION_KEY] = location
            preferences[AGE_KEY] = age
        }
    }

    suspend fun saveMedicalData(sensitivity: String, medicalHistory: String) {
        dataStore.edit { preferences ->
            preferences[SENSITIVITY_KEY] = sensitivity
            preferences[MEDHISTORY_KEY] = medicalHistory
            preferences[IS_FIRSTTIME_KEY] = false

        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[NAME_KEY] ?: "",
                preferences[LOCATION_KEY] ?: "",
                preferences[AGE_KEY] ?: 0,
                preferences[SENSITIVITY_KEY] ?: "",
                preferences[MEDHISTORY_KEY] ?: "",
                preferences[IS_FIRSTTIME_KEY] ?: true
            )
        }
    }

    suspend fun setIsFirstTime(b: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_FIRSTTIME_KEY] = false

        }
    }

    suspend fun getName(): String{
        return dataStore.data.first()[NAME_KEY] ?: ""
    }
    suspend fun getLocation(): String{
        return dataStore.data.first()[LOCATION_KEY] ?: ""
    }
    suspend fun getAge(): Int{
        return dataStore.data.first()[AGE_KEY] ?: 0
    }
    suspend fun getSensitivity(): String{
        return dataStore.data.first()[SENSITIVITY_KEY] ?: ""
    }
    suspend fun getMedHistory(): String{
        return dataStore.data.first()[MEDHISTORY_KEY] ?: ""
    }

    companion object {
        private val NAME_KEY = stringPreferencesKey("name")
        private val LOCATION_KEY = stringPreferencesKey("location")
        private val AGE_KEY = intPreferencesKey("age")
        private val SENSITIVITY_KEY = stringPreferencesKey("sensitivity")
        private val MEDHISTORY_KEY = stringPreferencesKey("medhistory")
        private val IS_FIRSTTIME_KEY = booleanPreferencesKey("isfirsttime")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

        fun getInstance(context: Context): UserPreference {
            val dataStore: DataStore<Preferences> = context.dataStore
            return UserPreference(dataStore, context)
        }
    }
}