package com.bangkit.h_airup.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build.*
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bangkit.h_airup.R
import com.bangkit.h_airup.dao.ApiDao
import com.bangkit.h_airup.database.AppDatabase
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.retrofit.ApiConfig

class FetchDataWorker(
    appContext: Context,
    params: WorkerParameters,
    userPreference: UserPreference
) : CoroutineWorker(appContext, params) {

    private val dao: ApiDao = AppDatabase.getInstance(applicationContext).apiResponseDao()
    private val city = userPreference.getCity()
    override suspend fun doWork(): Result {
        return try {
            Log.d("FetchDataWorker", "Worker is running...")

            // Make API call
            val response = ApiConfig.getApiService().getApi().execute()


            if (response.isSuccessful) {
                val apiResponse = response.body()

                // Save data to the database
                val apiData = ApiData(response = apiResponse)
                dao.saveAPIResponse(apiData)
                createNotification(
                    apiData.response?.aqi?.indexes?.get(0)?.category,
                    apiData.response?.aqi?.indexes?.get(0)?.aqiDisplay,
                    apiData.response?.weather?.weather?.get(0)?.main,
                    city,
                    apiData.response?.weather?.main?.feelsLike
                    )
                Result.success()
            } else {
                // Handle API call failure
                Log.e("FetchDataWorker", "API call failed with code ${response.code()}")
                Result.failure()
            }
        } catch (e: Exception) {
            // Handle exceptions
            Log.e("FetchDataWorker", "Error in worker: ${e.message}", e)
            Result.failure()
        }
    }

    private fun createNotification(aqiStatus: String?, aqiNumber: String?, weather: String?, city: Unit, temp: Any?) {

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManager)

        val notification = buildNotification(aqiStatus, aqiNumber, weather, city, temp)

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun buildNotification(aqiStatus: String?, aqiNumber: String?, weather: String?, city: Unit, temp: Any?): Notification {


        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.aqi_icon)
            .setContentTitle("AQI: $aqiNumber - $weather - $city")
            .setContentText("$aqiStatus - Feels like " + TempConvert.KelvinToCelsius(temp.toDouble()))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notification Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notification Channel Description"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "MyChannelId"
    }
}