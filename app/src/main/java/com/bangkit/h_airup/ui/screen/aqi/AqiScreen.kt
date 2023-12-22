package com.bangkit.h_airup.ui.screen.aqi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.pref.UserModel
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.response.ForecastResponse
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.ui.component.AqiPage
import com.bangkit.h_airup.ui.component.ForecastAqiTable
import com.bangkit.h_airup.ui.component.LoadingScreen
import com.bangkit.h_airup.ui.component.ProfileItem
import com.bangkit.h_airup.ui.theme.md_theme_light_primaryContainer
import com.bangkit.h_airup.utils.IconPicker
import com.bangkit.h_airup.utils.ShortenCity
import com.bangkit.h_airup.utils.categoryAqi
import com.bangkit.h_airup.utils.onProfileItemClick
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@RequiresApi(Build.VERSION_CODES.O)
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
    val forecastResponse by viewModel.forecastResponse.collectAsState()
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
        viewModel.getForecastResponse()
    }

    if (isLoading) {
        LoadingScreen()
    } else {
        AqiContent(
            viewModel,
            userModel = userModel,
            city,
            province,
            apiResponse,
            forecastResponse,
            navController
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AqiContent(
    viewModel: AqiViewModel,
    userModel: UserModel,
    city: String,
    province: String,
    apiResponse: ApiData?,
    forecastResponse: ForecastResponse?,
    navController: NavController

) {
    val isLoadingForecast by viewModel.isLoadingForecast.collectAsState()
    val lat: Double? =  apiResponse?.response?.coordinates?.lat
    val lon: Double? = apiResponse?.response?.coordinates?.lng
    var lokasi = LatLng(0.0, 0.0)
    if (lat != null && lon != null) {
        lokasi = LatLng(lat, lon)
    } else {
        Log.e("AqiScreen", "Latitude or longitude is null.")
    }

    LaunchedEffect(viewModel) {
        viewModel.getForecastResponse()
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(lokasi, 10f)
    }

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
                    .background(md_theme_light_primaryContainer)
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
                    aqiStatus = categoryAqi.getCategoryAqi(apiResponse?.response?.aqi?.indexes?.get(0)?.aqi),
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

                val aqiIndex = apiResponse?.response?.aqi?.indexes?.getOrNull(0)

                val aqiNumber = aqiIndex?.aqi
                val aqiIcon = IconPicker.getAqiIcon(aqiIndex?.aqi)
                val aqiStatus = aqiIndex?.category.toString()
                val weather = apiResponse?.response?.weather?.weather?.getOrNull(0)?.main
                val weatherIcon = IconPicker.getWeatherIcon(apiResponse?.response?.weather?.weather?.getOrNull(0)?.icon)


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
                                painter = painterResource(id = aqiIcon),
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
                                painter = painterResource(id = weatherIcon),
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


                if (!isLaunchedEffectExecuted) {
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(lokasi, 10f)
                    isLaunchedEffectExecuted = true
                }
            }
        }

        item{
            if (isLoadingForecast) {
                Box(
                    modifier = Modifier
                        .height(500.dp)
                        .fillMaxWidth()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }else {
                ForecastAqiTable(forecastResponse = forecastResponse)
            }
        }
    }
}
