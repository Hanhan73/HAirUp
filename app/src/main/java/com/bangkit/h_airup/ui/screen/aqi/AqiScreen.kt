package com.bangkit.h_airup.ui.screen.aqi

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bangkit.h_airup.R
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
import com.bangkit.h_airup.utils.ShortenCity
import com.bangkit.h_airup.utils.onProfileItemClick
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.tasks.await

@Composable
fun AqiScreen(
    modifier: Modifier = Modifier,
    userPreference: UserPreference,
    viewModel: AqiViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current), LocalContext.current)
    ),
    navController: NavController
) {
    val userModel by userPreference.getSession().collectAsState(initial = UserModel())
    val apiResponse by viewModel.apiResponse.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var city: String
    var province: String

    city = userModel.citygps
    province = userModel.provincegps

    if (!viewModel.isGps()){
        city = userModel.city
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
            apiResponse,
            navController
        )
    }
}

@Composable
fun AqiContent(
    userModel: UserModel,
    city: String,
    province: String,
    apiResponse: ApiData?,
    navController: NavController

) {
    val lat: Double? = apiResponse?.response?.coordinates?.lat
    val lon: Double? = apiResponse?.response?.coordinates?.lng
    // Correct order for LatLng
    var lokasi = LatLng(0.0, 0.0)
// Check if lat and lon are not null before using them
    if (lat != null && lon != null) {
        // Correct order for LatLng
        lokasi = LatLng(lat, lon)
    } else {
        // Handle the case where lat or lon is null
        Log.e("AqiScreen", "Latitude or longitude is null.")
    }



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
                    city = ShortenCity.shortenCityName(city),
                    province = province,
                    modifier = Modifier.clickable {
                        onProfileItemClick(navController)
                    }

                )

                AqiPage(
                    aqiNumber = apiResponse?.response?.aqi?.indexes?.get(0)?.aqi,
                    aqiStatus = apiResponse?.response?.aqi?.indexes?.get(0)?.category.toString(),
                    aqiPollutan = apiResponse?.response?.aqi?.indexes?.get(0)?.dominantPollutant,
                    pollutants = apiResponse?.response?.aqi?.pollutants
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
                Log.d("AqiScreen", "apiResponse: $apiResponse")

                val aqiIndex = apiResponse?.response?.aqi?.indexes?.getOrNull(0)
                Log.d("AqiScreen", "aqiIndex: $aqiIndex")

                val aqiNumber = aqiIndex?.aqi
                val aqiStatus = aqiIndex?.category.toString()
                val weather = apiResponse?.response?.weather?.weather?.getOrNull(0)?.description

                Log.d("AqiScreen", "aqiNumber: $aqiNumber")
                Log.d("AqiScreen", "aqiStatus: $aqiStatus")
                Log.d("AqiScreen", "weather: $weather")

                MarkerInfoWindow(
                    state = MarkerState(position = lokasi),
                ) { marker ->
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp)
                            )
                            .width(180.dp)
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .height(40.dp)
                                    .fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "AQI: $aqiNumber - $aqiStatus",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .height(40.dp)
                                    .fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Weather : $weather",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }


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
