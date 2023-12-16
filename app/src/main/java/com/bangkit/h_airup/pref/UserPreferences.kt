package com.bangkit.h_airup.pref

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>, private val context: Context) {

    suspend fun saveUserData(name: String, location: String, province: String, age: Int) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[LOCATION_KEY] = location
            preferences[PROVINCE_KEY] = province
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



    suspend fun setLocations(city:String, provinces:String, latitude:Double, longitude:Double) {
        dataStore.edit { preferences ->
            preferences[CITY_KEY] = city
            preferences[PROVINCES_KEY] = provinces
            preferences[LAT_KEY] = latitude
            preferences[LON_KEY] = longitude
        }
    }



    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            val userModel = UserModel(
                preferences[NAME_KEY] ?: "",
                preferences[LOCATION_KEY] ?: "",
                preferences[PROVINCE_KEY] ?: "",
                preferences[CITY_KEY] ?: "",
                preferences[PROVINCES_KEY] ?: "",
                preferences[LAT_KEY] ?: 0.0,
                preferences[LON_KEY] ?: 0.0,
                preferences[AGE_KEY] ?: 0,
                preferences[SENSITIVITY_KEY] ?: "",
                preferences[MEDHISTORY_KEY] ?: "",
                preferences[IS_FIRSTTIME_KEY] ?: true,
                preferences[IS_WORKMANAGERSTART_KEY] ?: false
            )

            Log.d("UserPreference", "Retrieved Latitude: ${userModel.lat}, Longitude: ${userModel.lon}")

            userModel
        }
    }

    suspend fun setIsFirstTime(b: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_FIRSTTIME_KEY] = false

        }
    }

    fun getStatus() {
        dataStore.data.map { preferences ->
            return@map preferences[SENSITIVITY_KEY]
        }
    }

    fun getCity() {
        dataStore.data.map { preferences ->
            return@map preferences[CITY_KEY]
        }
    }



    companion object {
        private val NAME_KEY = stringPreferencesKey("name")
        private val LOCATION_KEY = stringPreferencesKey("location")
        private val PROVINCE_KEY = stringPreferencesKey("province")
        private val CITY_KEY = stringPreferencesKey("city")
        private val PROVINCES_KEY = stringPreferencesKey("provinces")
        private val LAT_KEY = doublePreferencesKey("lat")
        private val LON_KEY = doublePreferencesKey("lon")
        private val AGE_KEY = intPreferencesKey("age")
        private val SENSITIVITY_KEY = stringPreferencesKey("sensitivity")
        private val MEDHISTORY_KEY = stringPreferencesKey("medhistory")
        private val IS_FIRSTTIME_KEY = booleanPreferencesKey("isfirsttime")
        private val IS_WORKMANAGERSTART_KEY = booleanPreferencesKey("isworkmanagerstart")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

        fun getInstance(context: Context): UserPreference {
            val dataStore: DataStore<Preferences> = context.dataStore
            return UserPreference(dataStore, context)
        }
    }
}