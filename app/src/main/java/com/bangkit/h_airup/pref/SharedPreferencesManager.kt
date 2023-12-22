package com.bangkit.h_airup.pref

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }

    fun saveRekomen(rekomen: String) {
        sharedPreferences.edit().putString("rekomen", rekomen).apply()
    }

    fun getRekomen(): String? {
        return sharedPreferences.getString("rekomen", null)
    }
}