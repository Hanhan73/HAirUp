package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bangkit.h_airup.R
import com.bangkit.h_airup.response.ForecastResponse
import com.bangkit.h_airup.utils.TempConvert
import com.bangkit.h_airup.utils.onProfileItemClick

@Composable
fun ForecastTable(
    forecastResponse: ForecastResponse?
){
    var expanded by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf("Bandung") }
    val cities = listOf("Bandung", "Semarang", "Jakarta")

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        userScrollEnabled = false,
        modifier = Modifier
            .height(500.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .background(MaterialTheme.colorScheme.primaryContainer)
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
                        color = MaterialTheme.colorScheme.onSurface
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

        selectedForecastdata?.let {
            items(it) { data ->
                forecastItem(
                    forecast  = data,
                    Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
        }

        selectedCurrentdata?.let {
            item(it) {
                currentForecastItem(
                    curr = it,
                    Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
        }

        selectedPastdata?.let {
            items(it) { data ->
                pastForecastItem(
                    past = data,
                    Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
        }

    }

}

