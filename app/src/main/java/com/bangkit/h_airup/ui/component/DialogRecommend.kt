package com.bangkit.h_airup.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bangkit.h_airup.model.RecommendRequestBody
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.screen.home.HomeViewModel
import com.bangkit.h_airup.ui.theme.md_theme_light_onPrimaryContainer
import com.bangkit.h_airup.ui.theme.md_theme_light_primary
import com.bangkit.h_airup.ui.theme.md_theme_light_primaryContainer
import com.bangkit.h_airup.ui.theme.md_theme_light_secondaryContainer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogRecommend(
    onDismiss: () -> Unit,
    userPreference: UserPreference,
    viewModel: HomeViewModel,
    recommendationCallback: RecommendationCallback

) {
    val coroutineScope = rememberCoroutineScope()
    val cities = listOf("Bandung", "Semarang", "Jakarta")
    val hari = listOf("1 Hari", "2 Hari", "3 Hari")

    var selectedCity by remember { mutableStateOf<String?>(null) }
    var selectedHari by remember { mutableStateOf<Int?>(null) }
    val timeState = rememberTimePickerState(is24Hour = true)

    Dialog(
        onDismissRequest = { onDismiss() },
        content = {
            Column(
                modifier = Modifier
                    .background(md_theme_light_secondaryContainer)
                    .padding(16.dp)
            ) {
                Text(
                    "Pilih Untuk Kota Mana: ",
                    fontSize = 8.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    cities.forEach { city ->
                        CityButton(city, selectedCity) {
                            selectedCity = it
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Pilih Untuk Berapa Hari Kedepan: ",
                    fontSize = 8.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    hari.forEach { day ->
                        DayButton(day, selectedHari) {
                            selectedHari = it
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TimePicker(
                    state = timeState,
                    layoutType = TimePickerLayoutType.Vertical
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        coroutineScope.launch {
                            val userId = userPreference.getUserId()
                            viewModel.hour = timeState.hour
                            viewModel.day = selectedHari ?: -1
                            viewModel.kota = selectedCity ?: ""

                            val requestBody = RecommendRequestBody(
                                timeState.hour,
                                selectedHari ?: 0,
                                selectedCity?.toLowerCase() ?: ""
                            )
                            val data = viewModel.postRecommendResponse(requestBody, userId)
                            Log.d("DialogRecommend", data.toString())
                            recommendationCallback.onRecommendationDataReceived(data)

                        }
                        onDismiss()
                    },
                        colors = ButtonDefaults.buttonColors(md_theme_light_primaryContainer)
                    ) {
                        Text("Save", color = md_theme_light_primary)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = {

                        onDismiss()
                    },
                        colors = ButtonDefaults.buttonColors(md_theme_light_primaryContainer)) {
                        Text("Cancel",color = md_theme_light_primary)
                    }
                }
            }
        }
    )
}

@Composable
private fun CityButton(city: String, selectedCity: String?, onClick: (String) -> Unit) {
    Button(
        onClick = { onClick(city) },
        modifier = Modifier
            .width(95.dp)
            .padding(horizontal = 2.dp)
            .background(
                color = md_theme_light_secondaryContainer,
                shape = MaterialTheme.shapes.medium
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selectedCity == city) md_theme_light_primaryContainer else MaterialTheme.colorScheme.background,
            contentColor = if (selectedCity == city) md_theme_light_onPrimaryContainer else MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            city,
            fontSize = 8.sp,
            color = if (selectedCity == city) md_theme_light_primary else MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun DayButton(day: String, selectedDay: Int?, onClick: (Int) -> Unit) {
    Button(
        onClick = { onClick(changeHaritoInt(day)) },
        modifier = Modifier
            .width(95.dp)
            .padding(horizontal = 2.dp)
            .background(
                color = md_theme_light_secondaryContainer,
                shape = MaterialTheme.shapes.medium
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selectedDay == changeHaritoInt(day)) md_theme_light_primaryContainer else MaterialTheme.colorScheme.background,
            contentColor = if (selectedDay == changeHaritoInt(day)) md_theme_light_onPrimaryContainer else MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            day,
            fontSize = 8.sp,
            color = if (selectedDay == changeHaritoInt(day)) md_theme_light_primary else MaterialTheme.colorScheme.primary
        )
    }
}

private fun changeHaritoInt(hari: String): Int {
    return when (hari.toLowerCase()) {
        "1 hari" -> 1
        "2 hari" -> 2
        "3 hari" -> 3
        else -> -1
    }
}