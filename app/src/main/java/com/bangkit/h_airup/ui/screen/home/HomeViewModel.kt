package com.bangkit.h_airup.ui.screen.home

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bangkit.h_airup.dao.ApiDao
import com.bangkit.h_airup.data.AqiRepository
import com.bangkit.h_airup.database.AppDatabase
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.model.ForecastData
import com.bangkit.h_airup.model.RecommendRequestBody
import com.bangkit.h_airup.model.WeatherData
import com.bangkit.h_airup.response.WeathersResponse
import com.bangkit.h_airup.response.APIResponse
import com.bangkit.h_airup.response.ForecastResponse
import com.bangkit.h_airup.response.RecommendResponse
import com.bangkit.h_airup.response.TestResponse
import com.bangkit.h_airup.response.TestWeatherResponse
import com.bangkit.h_airup.retrofit.ApiConfig
import com.bangkit.h_airup.utils.FetchDataWorker
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class HomeViewModel(
    private val repository: AqiRepository,
    private val context: Context,

) : ViewModel() {

    var hour by mutableIntStateOf(0)
    var day by mutableIntStateOf(0)
    var kota by mutableStateOf<String?>("")

    private val _apiResponse = MutableStateFlow<APIResponse?>(null)
    val apiResponse: StateFlow<APIResponse?> = _apiResponse

    private val _forecastResponse = MutableStateFlow<ForecastResponse?>(null)
    val forecastResponse: StateFlow<ForecastResponse?> = _forecastResponse

    private val _weatherResponse = MutableStateFlow<WeathersResponse?>(null)
    val weatherResponse: StateFlow<WeathersResponse?> = _weatherResponse

    private val _testResponse = MutableStateFlow<TestResponse?>(null)
    val testResponse: StateFlow<TestResponse?> = _testResponse

    private val _weatherTestResponse = MutableStateFlow<TestWeatherResponse?>(null)
    val weatherTestResponse: StateFlow<TestWeatherResponse?> = _weatherTestResponse

    private val _recommendResponse = MutableStateFlow<RecommendResponse?>(null)
    val recommendResponse: StateFlow<RecommendResponse?> = _recommendResponse

    private val mLocationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isLoadingForecast = MutableStateFlow(true)
    val isLoadingForecast: StateFlow<Boolean> = _isLoadingForecast

    private val dao: ApiDao = AppDatabase.getInstance(context).apiResponseDao()


    fun isGps() : Boolean = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    fun workPeriodic(userId: String) {
        val workManager = WorkManager.getInstance(context)
        val uniqueWorkName = "NOTIFICATIONS"

        if (workManager.getWorkInfosByTag(uniqueWorkName).get().isEmpty()) {
            val inputData = Data.Builder().putString("USER_ID", userId).build()
            val oneTimeWorkRequest = OneTimeWorkRequest.Builder(FetchDataWorker::class.java)
                .addTag(uniqueWorkName)
                .setInputData(inputData)
                .build()

            workManager.enqueue(oneTimeWorkRequest)

            val periodicWorkRequest = PeriodicWorkRequest.Builder(
                FetchDataWorker::class.java,
                1, TimeUnit.HOURS
            ).setInputData(inputData)
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

            val forecastData = ForecastData(forecastResponse = forecastResponse)
            dao.saveForecastResponse(forecastData)
            _forecastResponse.value = forecastResponse
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


    suspend fun postRecommendResponse(recommendRequestBody: RecommendRequestBody, userId: String): String?{
        return try {
            val client = ApiConfig.getApiService().createRecommendation(recommendRequestBody, userId)

            val response = suspendCoroutine<Response<RecommendResponse>> { continuation ->
                client.enqueue(object : Callback<RecommendResponse> {
                    override fun onResponse(call: Call<RecommendResponse>, response: Response<RecommendResponse>) {
                        continuation.resume(response)
                    }

                    override fun onFailure(call: Call<RecommendResponse>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })
            }

            if (response.isSuccessful) {
                val userIdFromResponse = response.body()?.response?.pesan


                Log.d("FormViewModel", userIdFromResponse.toString())

                userIdFromResponse
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("FormViewModel", "Error posting user: ${e.message}")
            null
        }
    }

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
            val weatherData = WeatherData(weatherResponse = weatherResponse)
            dao.saveWeatherResponse(weatherData)
            _weatherResponse.value = weatherResponse
            _isLoadingForecast.value = false
        } catch (e: JsonSyntaxException) {
            Log.e("HOMEVIEWMODEL", "JsonSyntaxException: ${e.message}")
        } catch (t: Throwable) {
            Log.e("HOMEVIEWMODEL", "onFailure: ${t.message}")
            _isLoadingForecast.value = false
        }
    }

    suspend fun getTestResponse() {
        try {
            val testResponse = suspendCancellableCoroutine<TestResponse> { continuation ->
                val client = ApiConfig.getApiService().getTest()
                client.enqueue(object : Callback<TestResponse> {
                    override fun onResponse(call: Call<TestResponse>, response: Response<TestResponse>) {
                        response.body()?.let { continuation.resume(it) }
                    }

                    override fun onFailure(call: Call<TestResponse>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })

                continuation.invokeOnCancellation {
                    client.cancel()
                }
            }


            _testResponse.value = testResponse
            _isLoadingForecast.value = false
        } catch (e: JsonSyntaxException) {
            Log.e("HOMEVIEWMODEL", "JsonSyntaxException: ${e.message}")
        } catch (t: Throwable) {
            Log.e("HOMEVIEWMODEL", "onFailure: ${t.message}")
            _isLoadingForecast.value = false
        }
    }

    suspend fun getRecommendResponse(userId: String) {
        try {
            val recommendResponse = suspendCancellableCoroutine<RecommendResponse> { continuation ->
                val client = ApiConfig.getApiService().getRecommendation(userId)
                client.enqueue(object : Callback<RecommendResponse> {
                    override fun onResponse(call: Call<RecommendResponse>, response: Response<RecommendResponse>) {
                        response.body()?.let { continuation.resume(it) }
                    }

                    override fun onFailure(call: Call<RecommendResponse>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                })

                continuation.invokeOnCancellation {
                    client.cancel()
                }
            }


            _recommendResponse.value = recommendResponse
        } catch (e: JsonSyntaxException) {
            Log.e("HOMEVIEWMODEL", "JsonSyntaxException: ${e.message}")
        } catch (t: Throwable) {
            Log.e("HOMEVIEWMODEL", "onFailure: ${t.message}")
        }
    }
}