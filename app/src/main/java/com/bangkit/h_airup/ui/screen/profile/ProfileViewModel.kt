package com.bangkit.h_airup.ui.screen.profile

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bangkit.h_airup.data.AqiRepository
import com.bangkit.h_airup.model.UserRequestBody
import com.bangkit.h_airup.pref.UserModel
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.response.UserResponse
import com.bangkit.h_airup.retrofit.ApiConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProfileViewModel(
    private val repository: AqiRepository,
    private val context: Context,

) : ViewModel() {


    private val userPreference = UserPreference.getInstance(context)

    var name by mutableStateOf("")
    var city by mutableStateOf("")
    var province by mutableStateOf("")
    var age by mutableStateOf("")
    var sensitivity by mutableStateOf("")
    var medHistory by mutableStateOf("")
    var filteredCities by mutableStateOf<List<String>>(emptyList())

    suspend fun initializeUserData(userPreference: UserPreference) {
        val userModel = userPreference.getSession().firstOrNull() ?: UserModel()
        name = userModel.name
        city = userModel.city
        province = userModel.province
        age = userModel.age.toString()
        sensitivity = userModel.sensitivity
        medHistory = userModel.medHistory
    }

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    suspend fun putUser(requestBody: UserRequestBody, userId: String): String? {
        return try {
            val client = ApiConfig.getApiService().putUser(requestBody, userId)

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