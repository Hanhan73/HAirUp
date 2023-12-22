package com.bangkit.h_airup.ui.component

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.h_airup.response.ForecastResponse
import com.bangkit.h_airup.response.WeathersResponse
import com.bangkit.h_airup.ui.theme.md_theme_light_onSecondaryContainer
import com.bangkit.h_airup.ui.theme.md_theme_light_secondaryContainer
import com.bangkit.h_airup.ui.theme.md_theme_light_tertiaryContainer
import com.bangkit.h_airup.utils.TempConvert

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastTable(
    forecastResponse: ForecastResponse?,
    weatherResponse: WeathersResponse?
){
    var expanded by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf("Bandung") }
    val cities = listOf("Bandung", "Semarang", "Jakarta")

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        userScrollEnabled = false,
        modifier = Modifier
            .height(750.dp)
            .background(md_theme_light_secondaryContainer)
    ) {
        item {
            Text(
                text = "Forecast",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                ),
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp),
                color = md_theme_light_onSecondaryContainer
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .background(md_theme_light_tertiaryContainer)
                    .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                    .padding(vertical = 16.dp, horizontal = 12.dp)
                    .clickable { expanded = !expanded }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedCity,
                        color = md_theme_light_onSecondaryContainer
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) { cities.forEach { city ->
                        DropdownMenuItem(
                            text = { Text(text = city) },
                            onClick = {
                                selectedCity = city
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        val selectedPastdata = when (selectedCity) {
            "Bandung" -> forecastResponse?.bandung?.pastDataAQI
            "Semarang" -> forecastResponse?.semarang?.pastDataAQI
            "Jakarta" -> forecastResponse?.jakarta?.pastDataAQI
            else -> null
        }
        val selectedCurrentdata = when (selectedCity) {
            "Bandung" -> forecastResponse?.bandung?.currentDataAQI
            "Semarang" -> forecastResponse?.semarang?.currentDataAQI
            "Jakarta" -> forecastResponse?.jakarta?.currentDataAQI
            else -> null
        }
        val selectedForecastdata = when (selectedCity) {
            "Bandung" -> forecastResponse?.bandung?.foreCastAQI
            "Semarang" -> forecastResponse?.semarang?.foreCastAQI
            "Jakarta" -> forecastResponse?.jakarta?.foreCastAQI
            else -> null
        }

        val selectedPastWeather = when (selectedCity) {
            "Bandung" -> weatherResponse?.bandung?.pastDataWeather
            "Semarang" -> weatherResponse?.semarang?.pastDataWeather
            "Jakarta" -> weatherResponse?.jakarta?.pastDataWeather
            else -> null
        }
        val selectedCurrentWeather = when (selectedCity) {
            "Bandung" -> weatherResponse?.bandung?.currentDataWeather
            "Semarang" -> weatherResponse?.semarang?.currentDataWeather
            "Jakarta" -> weatherResponse?.jakarta?.currentDataWeather
            else -> null
        }
        val selectedForecastWeather = when (selectedCity) {
            "Bandung" -> weatherResponse?.bandung?.foreCastWeather
            "Semarang" -> weatherResponse?.semarang?.foreCastWeather
            "Jakarta" -> weatherResponse?.jakarta?.foreCastWeather
            else -> null
        }

        Log.d("ASADDA", weatherResponse.toString())


        selectedForecastdata?.let {
            var i = 1
            items(it.reversed()) { data ->

                val daysAfter = it.indexOf(data) + 1
                val tempPredict = selectedForecastWeather?.get(i-1)?.tx


                forecastItem(
                    forecast = data,
                    Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                    daysAfter,
                    tempPredict
                )
                i++
            }
        }

        selectedCurrentdata?.let {
            item(it) {
                currentForecastItem(
                    curr = it,
                    Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                    TempConvert.KelvinToCelsiusn(selectedCurrentWeather?.main?.temp)

                )
            }
        }

        selectedPastdata?.let {
            var i = 1
            items(it) { data ->
                val daysAgo = it.indexOf(data) + 1 // calculate the number of days ago

                selectedPastWeather?.get(i)?.temp?.let { it1 ->
                    pastForecastItem(
                        past = data,
                        Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                        daysAgo = daysAgo,
                        it1.toInt()
                    )
                }
            }
            i++
        }

    }

}

