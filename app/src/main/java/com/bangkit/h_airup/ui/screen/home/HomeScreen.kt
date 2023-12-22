
package com.bangkit.h_airup.ui.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.pref.SharedPreferencesManager
import com.bangkit.h_airup.pref.UserModel
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.response.APIResponse
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.ui.component.AqiHome
import com.bangkit.h_airup.ui.component.ForecastTable
import com.bangkit.h_airup.ui.component.GreetingItem
import com.bangkit.h_airup.ui.component.LoadingScreen
import com.bangkit.h_airup.ui.component.ProfileItem
import com.bangkit.h_airup.ui.component.Recommendation
import com.bangkit.h_airup.ui.component.WeatherHome
import com.bangkit.h_airup.ui.theme.md_theme_light_secondaryContainer
import com.bangkit.h_airup.utils.IconPicker
import com.bangkit.h_airup.utils.ShortenCity
import com.bangkit.h_airup.utils.TempConvert
import com.bangkit.h_airup.utils.categoryAqi
import com.bangkit.h_airup.utils.onProfileItemClick


@RequiresApi(Build.VERSION_CODES.O)
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

    if (isLoading) {
        LoadingScreen()
    } else {
        HomeContent(
            userModel = userModel,
            userPreference,
            city,
            province,
            apiResponse,
            navController,
            viewModel
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeContent(
    userModel: UserModel,
    userPreference: UserPreference,
    city: String,
    province: String,
    apiResponse: APIResponse?,
    navController: NavController,
    viewModel: HomeViewModel

) {
    val isLoadingForecast by viewModel.isLoadingForecast.collectAsState()
    val forecastResponse by viewModel.forecastResponse.collectAsState()
    val weatherResponse by viewModel.weatherResponse.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.getTestResponse()
        viewModel.getWeatherTestResponse()
        viewModel.getForecastResponse()
        viewModel.getWeatherForecastResponse()
    }
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
                    .height(300.dp)
                    .paint(
                        painterResource(
                            id = IconPicker.getWeatherIllustration(
                                apiResponse?.weather?.weather?.get(
                                    0
                                )?.icon
                            )
                        ),
                        contentScale = ContentScale.Crop
                    )
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
                GreetingItem(
                    name = userModel.name,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row {
                    AqiHome(
                        aqiNumber = apiResponse?.aqi?.indexes?.get(0)?.aqi,
                        aqiStatus = categoryAqi.getCategoryAqi(apiResponse?.aqi?.indexes?.get(0)?.aqi),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp, end = 8.dp)
                    )
                    WeatherHome(
                        temp = TempConvert.KelvinToCelsius(
                            apiResponse?.weather?.main?.temp as? Double ?: 0.0
                        ),
                        status = apiResponse?.weather?.weather?.get(0)?.main.toString(),
                        icon = apiResponse?.weather?.weather?.get(0)?.icon.toString(),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }

        item {
            Recommendation(
                modifier = Modifier.background(md_theme_light_secondaryContainer),
                userPreference = userPreference,
                generalRecommend = apiResponse?.aqi?.healthRecommendations?.generalPopulation.toString(),
                rekomendasi = apiResponse?.rekomendasi?.get(0),
                sharedPreferencesManager = SharedPreferencesManager(context = LocalContext.current)
            )
        }

        item {
            if (isLoadingForecast) {
                Box(
                    modifier = Modifier
                        .height(750.dp)
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

                Box(
                    modifier = Modifier
                        .height(750.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {

                    ForecastTable(forecastResponse = forecastResponse, weatherResponse)
                }
            }
        }
    }
}
