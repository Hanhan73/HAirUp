package com.bangkit.h_airup

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.theme.HAirUpTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : ComponentActivity() {

    private lateinit var userPreference: UserPreference
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this as ComponentActivity)
    }
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isLocationPermissionGranted = false
    private var isNotificationPermissionGranted = false

        private fun requestLocationUpdates() {
            if (isLocationPermissionGranted) {
                try {
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
                } catch (e: SecurityException) {
                    Log.e("LocationUpdatesError", "SecurityException: ${e.message}")
                    Toast.makeText(this, "Location permission revoked", Toast.LENGTH_SHORT).show()
                }
            } else { Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show()
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){permissions ->

            isLocationPermissionGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: isLocationPermissionGranted
            isNotificationPermissionGranted = permissions[if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.POST_NOTIFICATIONS
            } else {

            }] ?: isNotificationPermissionGranted

        }

        requestPermissions()

        if (isLocationPermissionGranted){
            requestLocationUpdates()
        }

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
    }

    private fun requestPermissions(){
        isLocationPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        isNotificationPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        val permissionRequest : MutableList<String> = ArrayList()

        if (!isLocationPermissionGranted){
            permissionRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!isNotificationPermissionGranted){
            permissionRequest.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        if (permissionRequest.isNotEmpty()){
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HAirUpTheme {
    }
}