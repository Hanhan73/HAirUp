package com.bangkit.h_airup.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build.*
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bangkit.h_airup.R
import com.bangkit.h_airup.dao.ApiDao
import com.bangkit.h_airup.database.AppDatabase
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.pref.UserModel
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.retrofit.ApiConfig

class FetchDataWorker(
    appContext: Context,
    params: WorkerParameters,
) : CoroutineWorker(appContext, params) {

    private val dao: ApiDao = AppDatabase.getInstance(applicationContext).apiResponseDao()
    private val userPreference = UserPreference.getInstance(appContext)
    private val userId = inputData.getString("USER_ID")

    override suspend fun doWork(): Result {

            return try {
                Log.d("FetchDataWorker", userPreference.getCity())
                // Make API call
                val response = ApiConfig.getApiService().getApi(userId.toString()).execute()
                val city: String = userPreference.getCity()
                if (response.isSuccessful) {
                    val apiResponse = response.body()

                    // Save data to the database
                    val apiData = ApiData(response = apiResponse)
                    dao.saveAPIResponse(apiData)
                    createNotification(
                        apiData.response?.aqi?.indexes?.get(0)?.category,
                        apiData.response?.aqi?.indexes?.get(0)?.aqiDisplay,
                        apiData.response?.weather?.weather?.get(0)?.description,
                        city,
                        TempConvert.KelvinToCelsiusn(apiData.response?.weather?.main?.feelsLike)
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


    private fun createNotification(aqiStatus: String?, aqiNumber: String?, weather: String?, city: String, temp: Int) {

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(notificationManager)

        val notification = buildNotification(aqiStatus, aqiNumber, weather, city, temp)

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun buildNotification(aqiStatus: String?, aqiNumber: String?, weather: String?, city: String, temp: Int): Notification {

        // Create an intent that opens the home screen
        val intent = applicationContext.packageManager.getLaunchIntentForPackage(applicationContext.packageName)
        intent?.action = Intent.ACTION_MAIN
        intent?.addCategory(Intent.CATEGORY_LAUNCHER)

        // Create the PendingIntent
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build and return the notification
        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.aqi_icon)
            .setContentTitle("AQI: $aqiNumber - $weather - $city")
            .setContentText("$aqiStatus - Feels like $temp°C")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)  // Set the PendingIntent
            .setAutoCancel(true)  // Automatically removes the notification when tapped
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