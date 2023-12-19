@file:OptIn(ExperimentalPermissionsApi::class)

package com.bangkit.h_airup.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.bangkit.h_airup.R
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.model.Aqi
import com.bangkit.h_airup.model.AqiData
import com.bangkit.h_airup.model.LocationData
import com.bangkit.h_airup.pref.UserModel
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.response.APIResponse
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.ui.component.AqiHome
import com.bangkit.h_airup.ui.component.ForecastItem
import com.bangkit.h_airup.ui.component.GreetingItem
import com.bangkit.h_airup.ui.component.LoadingScreen
import com.bangkit.h_airup.ui.component.ProfileItem
import com.bangkit.h_airup.ui.component.Recommendation
import com.bangkit.h_airup.ui.component.WeatherHome
import com.bangkit.h_airup.ui.navigation.Screen
import com.bangkit.h_airup.utils.TempConvert
import com.bangkit.h_airup.utils.onProfileItemClick
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userPreference: UserPreference,
    viewModel: HomeViewModel = viewModel(
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

    if (!viewModel.isGps()) {
        city = userModel.city
        province = userModel.province
    }

    LaunchedEffect(viewModel) {
        var userId: String = userModel.userId
        viewModel.getApiResponse(userId)
        viewModel.workPeriodic(userId)
    }

    // Check the loading state and show the appropriate content
    if (isLoading) {
        // Show loading indicator
        LoadingScreen()
    } else {
        // Data has loaded, show the content
        HomeContent(
            userModel = userModel,
            aqis = AqiData.aqis,
            userPreference,
            city,
            province,
            apiResponse,
            navController
        )
    }
}

@Composable
fun HomeContent(
    userModel: UserModel,
    aqis: List<Aqi>,
    userPreference: UserPreference,
    city: String,
    province: String,
    apiResponse: APIResponse?,
    navController: NavController

) {

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
                    .paint(painterResource(id = R.drawable.clear_background), contentScale = ContentScale.FillBounds)
                    .padding(bottom = 16.dp, top = 40.dp)
            ) {
                ProfileItem(
                    name = userModel.name,
                    city = city,
                    province = province,
                    modifier = Modifier.clickable {
                        onProfileItemClick(navController)
                    }

                )
                GreetingItem(
                    name = userModel.name,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row {
                    AqiHome(
                        aqiNumber = apiResponse?.aqi?.indexes?.get(0)?.aqi as? Int ?: 0,
                        aqiStatus = apiResponse?.aqi?.indexes?.get(0)?.category.toString(),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp, end = 8.dp)
                    )
                    WeatherHome(
                        temp = TempConvert.KelvinToCelsius(apiResponse?.weather?.main?.temp as? Double ?: 0.0),
                        status = apiResponse?.weather?.weather?.get(0)?.description.toString(),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
                }
        }

        item {
            Recommendation(
                modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                userPreference = userPreference,
                generalRecommend = apiResponse?.aqi?.healthRecommendations?.generalPopulation.toString(),
                rekomendasi = apiResponse?.rekomendasi?.get(0)
            )
        }

        items(aqis) { data ->

            ForecastItem(
                aqi = data,
                Modifier.background(MaterialTheme.colorScheme.primaryContainer)
            )
        }
    }
}



//@Preview(showSystemUi = true, device = Devices.PIXEL_4)
//@Composable
//fun HomeScreenPreview() {
//    HomeContent(aqis = AqiData.aqis)
//}

