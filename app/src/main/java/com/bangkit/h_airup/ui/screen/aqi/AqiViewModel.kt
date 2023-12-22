package com.bangkit.h_airup.ui.screen.aqi

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.h_airup.data.AqiRepository
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.response.ForecastResponse
import com.bangkit.h_airup.response.TestResponse
import com.bangkit.h_airup.retrofit.ApiConfig
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AqiViewModel(
    private val aqiRepository: AqiRepository,
    private val context: Context,
) : ViewModel() {

    private val _apiResponse = MutableStateFlow<ApiData?>(null)
    val apiResponse: StateFlow<ApiData?> = _apiResponse

    private val _forecastResponse = MutableStateFlow<ForecastResponse?>(null)
    val forecastResponse: StateFlow<ForecastResponse?> = _forecastResponse

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val mLocationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager

    fun isGps(): Boolean = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    private val _testResponse = MutableStateFlow<TestResponse?>(null)
    val testResponse: StateFlow<TestResponse?> = _testResponse

    private val _isLoadingForecast = MutableStateFlow(true)
    val isLoadingForecast: StateFlow<Boolean> = _isLoadingForecast

    fun getLocalData() {
        viewModelScope.launch {
            try {
                val localData = aqiRepository.getLatestAPIResponse()
                _apiResponse.value = localData
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    suspend fun getForecastResponse() {
        try {
            val forecastResponse = suspendCancellableCoroutine<ForecastResponse> { continuation ->
                val client = ApiConfig.getApiService().getForecast()
                client.enqueue(object : Callback<ForecastResponse> {
                    override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                        response.body()?.let { continuation.resume(it) }
                    }

                    override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })
                continuation.invokeOnCancellation {
                    client.cancel()
                }
            }

            _forecastResponse.value = forecastResponse
            _isLoadingForecast.value = false
        } catch (e: JsonSyntaxException) {
            Log.e("HOMEVIEWMODEL", "JsonSyntaxException: ${e.message}")
        } catch (t: Throwable) {
            Log.e("HOMEVIEWMODEL", "onFailure: ${t.message}")
            _isLoadingForecast.value = false
        }
    }




}