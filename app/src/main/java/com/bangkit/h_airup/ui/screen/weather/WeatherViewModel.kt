package com.bangkit.h_airup.ui.screen.weather

import android.content.Context
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.h_airup.data.AqiRepository
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.response.TestWeatherResponse
import com.bangkit.h_airup.response.WeathersResponse
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

    private val _weatherResponse = MutableStateFlow<WeathersResponse?>(null)
    val weatherResponse: StateFlow<WeathersResponse?> = _weatherResponse

    private val _isLoadingForecast = MutableStateFlow(true)
    val isLoadingForecast: StateFlow<Boolean> = _isLoadingForecast

    private val _weatherTestResponse = MutableStateFlow<TestWeatherResponse?>(null)
    val weatherTestResponse: StateFlow<TestWeatherResponse?> = _weatherTestResponse

    suspend fun getWeatherForecastResponse() {
        try {
            val weatherResponse = suspendCancellableCoroutine<WeathersResponse> { continuation ->
                val client = ApiConfig.getApiService().getForecastWeather()
                client.enqueue(object : Callback<WeathersResponse> {
                    override fun onResponse(call: Call<WeathersResponse>, response: Response<WeathersResponse>) {
                        response.body()?.let { continuation.resume(it) }
                    }

                    override fun onFailure(call: Call<WeathersResponse>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })

                continuation.invokeOnCancellation {
                    client.cancel()
                }
            }
            _weatherResponse.value = weatherResponse
            _isLoadingForecast.value = false
        } catch (e: JsonSyntaxException) {
            Log.e("HOMEVIEWMODEL", "JsonSyntaxException: ${e.message}")
        } catch (t: Throwable) {
            Log.e("HOMEVIEWMODEL", "onFailure: ${t.message}")
            _isLoadingForecast.value = false
        }
    }

    suspend fun getWeatherTestResponse() {
        try {
            val weatherTest = suspendCancellableCoroutine<TestWeatherResponse> { continuation ->
                val client = ApiConfig.getApiService().getTestWeather()
                client.enqueue(object : Callback<TestWeatherResponse> {
                    override fun onResponse(call: Call<TestWeatherResponse>, response: Response<TestWeatherResponse>) {
                        response.body()?.let { continuation.resume(it) }
                    }

                    override fun onFailure(call: Call<TestWeatherResponse>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })

                continuation.invokeOnCancellation {
                    client.cancel()
                }
            }


            _weatherTestResponse.value = weatherTest
            _isLoadingForecast.value = false
        } catch (e: JsonSyntaxException) {
            Log.e("HOMEVIEWMODEL", "JsonSyntaxException: ${e.message}")
        } catch (t: Throwable) {
            Log.e("HOMEVIEWMODEL", "onFailure: ${t.message}")
            _isLoadingForecast.value = false
        }
    }

    fun getLocalData() {
        viewModelScope.launch {
            try {
                val localData = repository.getLatestAPIResponse()
                _apiResponse.value = localData
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
    }