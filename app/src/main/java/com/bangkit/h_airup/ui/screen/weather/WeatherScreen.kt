package com.bangkit.h_airup.ui.screen.weather

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bangkit.h_airup.R
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.model.ApiData
import com.bangkit.h_airup.pref.UserModel
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.response.Weather
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.ui.component.AqiPage
import com.bangkit.h_airup.ui.component.LoadingScreen
import com.bangkit.h_airup.ui.component.ProfileItem
import com.bangkit.h_airup.ui.component.WeatherGridItem
import com.bangkit.h_airup.ui.screen.aqi.AqiContent
import com.bangkit.h_airup.ui.screen.aqi.AqiViewModel
import com.bangkit.h_airup.utils.ConvertTime
import com.bangkit.h_airup.utils.ShortenCity
import com.bangkit.h_airup.utils.TempConvert
import com.bangkit.h_airup.utils.onProfileItemClick
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    userPreference: UserPreference,
    viewModel: WeatherViewModel = viewModel(
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
        WeatherContent(
            userModel = userModel,
            city,
            province,
            apiResponse,
            navController
        )
    }

}

@Composable
fun WeatherContent(
    userModel: UserModel,
    city: String,
    province: String,
    apiResponse: ApiData?,
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

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
                ) {
                    // Temperature
                    Column {
                        Text(
                            text = "${TempConvert.KelvinToCelsius(apiResponse?.response?.weather?.main?.temp as? Double ?: 0.0)}°C",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Text(
                            text = "Feels like ${TempConvert.KelvinToCelsius(apiResponse?.response?.weather?.main?.feelsLike ?: 0.0)}°C",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    // Icon
                    Image(
                        painter = painterResource(id = R.drawable.d09),
                        contentDescription = null,
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp)
                            .aspectRatio(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.height(250.dp),
                    userScrollEnabled = false) {
                    item {
                        WeatherGridItem(
                            title = "Humidity",
                            iconRes = R.drawable.weather_icon,
                            text = apiResponse?.response?.weather?.main?.humidity.toString(),
                        )
                    }
                    item {
                        WeatherGridItem(
                            title = "Wind",
                            iconRes = R.drawable.weather_icon,
                            text = apiResponse?.response?.weather?.wind?.speed.toString(),
                        )
                    }
                    item {
                        WeatherGridItem(
                            title = "Sunrise",
                            iconRes = R.drawable.weather_icon,
                            text = ConvertTime.convertToTime(apiResponse?.response?.weather?.sys?.sunrise?.toLong()),
                        )
                    }
                    item {
                        WeatherGridItem(
                            title = "Sunset",
                            iconRes = R.drawable.weather_icon,
                            text = ConvertTime.convertToTime(apiResponse?.response?.weather?.sys?.sunset?.toLong()),
                        )
                    }
                }
            }
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
//@Composable
//fun WeatherScreenPreview(){
//    WeatherScreen()
//}
