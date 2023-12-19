package com.bangkit.h_airup.ui.screen.welcome

import android.content.Context
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.h_airup.data.AqiRepository
import com.bangkit.h_airup.database.AppDatabase
import com.bangkit.h_airup.model.UserEntity
import com.bangkit.h_airup.model.UserRequestBody
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.response.UserResponse
import com.bangkit.h_airup.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class FormViewModel(
    private val repository: AqiRepository,
    private val context: Context,
) : ViewModel() {
    var name by mutableStateOf("")
    var city by mutableStateOf("")
    var province by mutableStateOf("")
    var age by mutableStateOf("")
    var sensitivity by mutableStateOf("")
    var medHistory by mutableStateOf("")
    var filteredCities by mutableStateOf<List<String>>(emptyList())

    private var _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    // Callback to notify when userId is available
    private var userIdCallback: ((String) -> Unit)? = null

    fun setUserIdCallback(callback: (String) -> Unit) {
        userIdCallback = callback
        Log.d("FormViewModel", "UserId callback set")

    }

    private val mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun isGps(): Boolean = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    suspend fun postUser(requestBody: UserRequestBody): String? {
        return try {
            val client = ApiConfig.getApiService().postUser(requestBody)

            // Use suspendCoroutine to convert the callback-based API call to a suspend function
            val response = suspendCoroutine<Response<UserResponse>> { continuation ->
                client.enqueue(object : Callback<UserResponse> {
                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        continuation.resume(response)
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })
            }

            if (response.isSuccessful) {
                val userIdFromResponse = response.body()?.user?.id.toString()


                Log.d("FormViewModel", "User inserted successfully into Room database")

                userIdFromResponse
            } else {
                // Handle unsuccessful response
                null
            }
        } catch (e: Exception) {
            Log.e("FormViewModel", "Error posting user: ${e.message}")
            null
        }
    }
}