package com.bangkit.h_airup.ui

import android.content.Context
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.h_airup.dao.ApiDao
import com.bangkit.h_airup.data.AqiRepository
import com.bangkit.h_airup.pref.UserModel
import com.bangkit.h_airup.ui.screen.aqi.AqiViewModel
import com.bangkit.h_airup.ui.screen.home.HomeViewModel
import com.bangkit.h_airup.ui.screen.profile.ProfileViewModel
import com.bangkit.h_airup.ui.screen.weather.WeatherViewModel
import com.bangkit.h_airup.ui.screen.welcome.FormViewModel


class ViewModelFactory(
    private val repository: AqiRepository,
    private val applicationContext: Context,
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormViewModel::class.java)) {
            return FormViewModel(repository, applicationContext) as T
        }
        else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository, applicationContext) as T
        }
        else if (modelClass.isAssignableFrom(AqiViewModel::class.java)) {
            return AqiViewModel(repository, applicationContext) as T
        }
        else if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository, applicationContext) as T
        }
        else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository, applicationContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}