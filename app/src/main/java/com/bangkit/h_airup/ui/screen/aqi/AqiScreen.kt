package com.bangkit.h_airup.ui.screen.aqi

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.pref.UserModel
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.response.APIResponse
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.ui.component.AqiPage
import com.bangkit.h_airup.ui.component.LoadingScreen
import com.bangkit.h_airup.ui.component.ProfileItem
import com.bangkit.h_airup.ui.screen.home.HomeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.tasks.await

@Composable
fun AqiScreen(
    modifier: Modifier = Modifier,
    userPreference: UserPreference,
    viewModel: AqiViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current), LocalContext.current)
    )
) {
    val userModel by userPreference.getSession().collectAsState(initial = UserModel())
    val apiResponse by viewModel.apiResponse.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var city: String
    var province: String

    city = userModel.city
    province = userModel.provinces

    if (!viewModel.isGps()){
        city = userModel.location
        province = userModel.province
    }

    LaunchedEffect(viewModel) {
        viewModel.getLocalData()
    }

    if (isLoading) {
        // Show loading indicator
        LoadingScreen()
    } else {
        // Data has loaded, show the content
        AqiContent(
            userModel = userModel,
            city,
            province,
            apiResponse
        )
    }
}

@Composable
fun AqiContent(
    userModel: UserModel,
    city: String,
    province: String,
    apiResponse: ApiData?
) {
    val lat: Double = userModel.lat
    val lon: Double = userModel.lon

    // Correct order for LatLng
    val lokasi = LatLng(lat, lon)

    // Correct order for CameraPosition
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(lokasi, 10f)
    }

    // Flag to track if LaunchedEffect has already been executed
    var isLaunchedEffectExecuted by remember { mutableStateOf(false) }

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(bottom = 16.dp, top = 40.dp)
            ) {
                ProfileItem(
                    name = userModel.name,
                    city = city,
                    province = province
                )

                AqiPage(
                    aqiNumber = apiResponse?.response?.aqi?.indexes?.get(0)?.aqi,
                    aqiStatus = apiResponse?.response?.aqi?.indexes?.get(0)?.category.toString(),
                    aqiPollutan = 32.2
                )
            }
        }
        item {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f),
                cameraPositionState = cameraPositionState
            ) {
                // Additional map configuration or interactions if needed

                // Execute LaunchedEffect only once
                if (!isLaunchedEffectExecuted) {
                    // Set the initial camera position when the map is first shown
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(lokasi, 10f)
                    isLaunchedEffectExecuted = true
                }
            }
        }
    }
}
