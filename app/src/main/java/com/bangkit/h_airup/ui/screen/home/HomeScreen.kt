package com.bangkit.h_airup.ui.screen.home

import android.provider.CalendarContract.Colors
import android.provider.ContactsContract.CommonDataKinds.Phone
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.h_airup.model.Aqi
import com.bangkit.h_airup.model.AqiData
import com.bangkit.h_airup.pref.UserModel
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.component.AqiHome
import com.bangkit.h_airup.ui.component.ForecastItem
import com.bangkit.h_airup.ui.component.GreetingItem
import com.bangkit.h_airup.ui.component.ProfileItem
import com.bangkit.h_airup.ui.component.Recommendation
import com.bangkit.h_airup.ui.component.WeatherHome
import com.bangkit.h_airup.ui.theme.HAirUpTheme
import com.google.accompanist.pager.ExperimentalPagerApi

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userPreference: UserPreference
) {
    val userModel by userPreference.getSession().collectAsState(initial = UserModel())


    HomeContent(
        userModel = userModel,
        aqis = AqiData.aqis
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeContent(
    userModel : UserModel,
    aqis: List<Aqi>
) {

    Column(
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, top = 40.dp)
            ) {
                ProfileItem(
                    name = userModel.name,
                    location = userModel.location
                )
                GreetingItem(
                    name = userModel.name,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row {

                    AqiHome(
                        aqiNumber = 50,
                        aqiStatus = "Unhealthy",
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    )
                    WeatherHome(
                        temp = 36,
                        status = "Rain",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Recommendation()

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Text(
                text = "Forecast",
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp),
            ) {
                items(aqis) { data ->
                    ForecastItem(
                        aqi = data,
                        color = Color.Yellow
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


//@Preview(showSystemUi = true, device = Devices.PIXEL_4)
//@Composable
//fun HomeScreenPreview() {
//    HomeContent(aqis = AqiData.aqis)
//}

