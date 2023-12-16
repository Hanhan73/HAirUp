package com.bangkit.h_airup.ui.screen.home

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.h_airup.dao.ApiDao
import com.bangkit.h_airup.data.AqiRepository
import com.bangkit.h_airup.model.ApiData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.bangkit.h_airup.model.LocationModel
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.response.APIResponse
import com.bangkit.h_airup.retrofit.ApiConfig
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class HomeViewModel(
    private val repository: AqiRepository,
    private val context: Context,

) : ViewModel() {

    private val _apiResponse = MutableStateFlow<APIResponse?>(null)
    val apiResponse: StateFlow<APIResponse?> = _apiResponse

    private val mLocationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun isGps() : Boolean = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)


    fun getApiResponse() {
        val client = ApiConfig.getApiService().getApi()
        client.enqueue(object : Callback<APIResponse> {
            override fun onResponse(
                call: Call<APIResponse>,
                response: Response<APIResponse>
            ) {
                try {
                    val apiResponse = response.body()
                    _apiResponse.value = apiResponse
                    // Update loading state to false when data is loaded
                    _isLoading.value = false
                } catch (e: JsonSyntaxException) {
                    Log.e("HOMEVIEWMODEL", "JsonSyntaxException: ${e.message}")
                }
            }

            override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                Log.e("HOMEVIEWMODEL", "onFailure: ${t.message}")
                // Update loading state to false on failure
                _isLoading.value = false
            }
        })
    }
}