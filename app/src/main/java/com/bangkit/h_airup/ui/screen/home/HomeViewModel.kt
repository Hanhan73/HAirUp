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
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bangkit.h_airup.dao.ApiDao
import com.bangkit.h_airup.data.AqiRepository
import com.bangkit.h_airup.database.AppDatabase
import com.bangkit.h_airup.model.ApiData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.bangkit.h_airup.model.LocationModel
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.response.APIResponse
import com.bangkit.h_airup.retrofit.ApiConfig
import com.bangkit.h_airup.utils.FetchDataWorker
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class HomeViewModel(
    private val repository: AqiRepository,
    private val context: Context,

) : ViewModel() {

    private val _apiResponse = MutableStateFlow<APIResponse?>(null)
    val apiResponse: StateFlow<APIResponse?> = _apiResponse

    private val mLocationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val dao: ApiDao = AppDatabase.getInstance(context).apiResponseDao()


    fun isGps() : Boolean = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    fun workPeriodic(userId: String) {
        val workManager = WorkManager.getInstance(context)
        val uniqueWorkName = "NOTIFICATIONS"

        if (workManager.getWorkInfosByTag(uniqueWorkName).get().isEmpty()) {
            // Set userId as input data
            val inputData = Data.Builder().putString("USER_ID", userId).build()

            // Schedule a unique OneTimeWorkRequest to trigger the periodic work
            val oneTimeWorkRequest = OneTimeWorkRequest.Builder(FetchDataWorker::class.java)
                .addTag(uniqueWorkName)
                .setInputData(inputData) // Pass userId as input data
                .build()

            workManager.enqueue(oneTimeWorkRequest)

            // Schedule the periodic work
            val periodicWorkRequest = PeriodicWorkRequest.Builder(
                FetchDataWorker::class.java,
                1, TimeUnit.HOURS
            ).setInputData(inputData) // Pass userId as input data
                .build()

            workManager.enqueueUniquePeriodicWork(
                "NOTIFICATIONS",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
        }
    }


    suspend fun getApiResponse(userId: String) {
        try {
            val apiResponse = suspendCancellableCoroutine<APIResponse> { continuation ->
                val client = ApiConfig.getApiService().getApi(userId)
                client.enqueue(object : Callback<APIResponse> {
                    override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                        response.body()?.let { continuation.resume(it) }
                    }

                    override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })

                // This block will be executed if the coroutine is cancelled
                continuation.invokeOnCancellation {
                    client.cancel()
                }
            }

            val apiData = ApiData(response = apiResponse)
            dao.saveAPIResponse(apiData)
            _apiResponse.value = apiResponse
            _isLoading.value = false
        } catch (e: JsonSyntaxException) {
            Log.e("HOMEVIEWMODEL", "JsonSyntaxException: ${e.message}")
        } catch (t: Throwable) {
            Log.e("HOMEVIEWMODEL", "onFailure: ${t.message}")
            _isLoading.value = false
        }
    }
}