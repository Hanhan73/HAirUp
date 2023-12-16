package com.bangkit.h_airup

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.screen.welcome.showToast
import com.bangkit.h_airup.ui.theme.HAirUpTheme
import com.bangkit.h_airup.utils.FetchDataWorker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private lateinit var userPreference: UserPreference
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this as ComponentActivity)
    }

    // Create a launcher for the location permission request
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Location permission granted, show a confirmation and then request notification permission
                showToast("Location permission granted. Now let's enable notifications.")
                requestNotificationPermission()
            } else {
                // Permission denied
                // Handle the case where the user denied the location permission
                // You might want to show a message or disable location-dependent features
                // e.g., showPermissionDeniedMessage()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPreference = UserPreference.getInstance(this)

        setContent {
            HAirUpTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HAirUpApp()
                }
            }
        }
        if (!isLocationPermissionGranted()) {
            requestLocationPermission()
        } else {
            requestLocationUpdates()
        }

        val workManager = WorkManager.getInstance(applicationContext)
        val uniqueWorkName = "fetch_notifications"

        if (workManager.getWorkInfosByTag(uniqueWorkName).get().isEmpty()) {
            // Schedule a unique OneTimeWorkRequest to trigger the periodic work
            val oneTimeWorkRequest = OneTimeWorkRequest.Builder(FetchDataWorker::class.java)
                .addTag(uniqueWorkName)
                .build()

            workManager.enqueue(oneTimeWorkRequest)

            // Schedule the periodic work
            val periodicWorkRequest = PeriodicWorkRequest.Builder(
                FetchDataWorker::class.java,
                1, TimeUnit.HOURS
            ).build()

            workManager.enqueueUniquePeriodicWork(
                "fetch_notifications",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
        }
    }

    private fun requestNotificationPermission() {
        val notificationManager = NotificationManagerCompat.from(this)

        if (!notificationManager.areNotificationsEnabled()) {
            // Notifications are disabled, show a confirmation and ask the user to enable them
            showToast("Please enable notifications for the best experience.")
            // You can also provide a button or navigate the user to the notification settings page
            // Example: Open the app settings
            val intent = Intent().apply {
                action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = android.net.Uri.fromParts("package", packageName, null)
            }
            startActivity(intent)
        } else {
            // Notifications are already enabled
            showToast("Notifications are already enabled.")
        }
    }
    private fun getProvinceData(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(this)
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses != null) {
            return if (addresses.isNotEmpty()) {
                addresses[0].adminArea ?: "Unknown Province"
            } else {
                "Unknown Province"
            }
        }
        return "Province Placeholder"
    }

    private fun getCityData(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(this)
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                return addresses[0].subAdminArea ?: "Unknown City"
            } else {
                return "Unknown City"
            }
        } catch (e: IOException) {
            Log.e("GeocodingError", "Error during geocoding: ${e.message}")
            return "Unknown City"
        }
    }

    // Function to check if the location permission is granted
    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationUpdates() {
        if (isLocationPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    lifecycleScope.launch {
                        try {
                            val latitude = it.latitude
                            val longitude = it.longitude
                            val city = getCityData(latitude, longitude)
                            val province = getProvinceData(latitude, longitude)
                            Log.d("MAIN", "Latitude: $latitude, Longitude: $longitude, City: $city, Province: $province")

                            userPreference.setLocations(city, province, latitude, longitude)
                        } catch (e: Exception) {
                            Log.e("LocationUpdatesError", "Error during location updates: ${e.message}")
                        }
                    }
                } ?: Log.e("LocationUpdatesError", "Location is null")
            }.addOnFailureListener { e ->
                Log.e("LocationUpdatesError", "Error getting location: ${e.message}")
            }
        }
    }

    private fun requestLocationPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HAirUpTheme {
    }
}