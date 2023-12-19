package com.bangkit.h_airup.ui.screen.weather

import android.content.Context
import android.location.LocationManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.h_airup.data.AqiRepository
import com.bangkit.h_airup.model.ApiData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: AqiRepository,
    private val context: Context,

    ) : ViewModel() {
    private val _apiResponse = MutableStateFlow<ApiData?>(null)
    val apiResponse: StateFlow<ApiData?> = _apiResponse

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun isGps(): Boolean = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)



    fun getLocalData() {
        viewModelScope.launch {
            try {
                val localData = repository.getLatestAPIResponse()
                _apiResponse.value = localData
                _isLoading.value = false
            } catch (e: Exception) {
                // Handle the exception if needed
                _isLoading.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
    }