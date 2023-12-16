package com.bangkit.h_airup.ui.screen.aqi

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.h_airup.dao.ApiDao
import com.bangkit.h_airup.data.AqiRepository
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.response.APIResponse
import com.bangkit.h_airup.retrofit.ApiConfig
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AqiViewModel(
    private val aqiRepository: AqiRepository,
    private val context: Context,
) : ViewModel() {

    private val _apiResponse = MutableStateFlow<ApiData?>(null)
    val apiResponse: StateFlow<ApiData?> = _apiResponse

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val mLocationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager

    fun isGps(): Boolean = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)



    fun getLocalData() {
        viewModelScope.launch {
            try {
                val localData = aqiRepository.getLatestAPIResponse()
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