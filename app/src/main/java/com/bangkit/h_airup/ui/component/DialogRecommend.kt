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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.ui.screen.welcome.FormViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogRecommend(
    onDismiss: () -> Unit,
    userPreference: UserPreference,
    viewModel: FormViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current), LocalContext.current)
    )
) {
    val coroutineScope = rememberCoroutineScope()
    val cities = listOf("Bandung", "Semarang", "Jakarta")

    val timeState = rememberTimePickerState(is24Hour = true) // Set displayMinutes to false
    var selectedCity by remember { mutableStateOf<String?>(null) }

    Dialog(
        onDismissRequest = { onDismiss() },
        content = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp)
            ) {
                Row {
                    cities.forEach { city ->
                        Button(
                            onClick = {
                                // Handle button click based on the city
                                Log.d("DialogRecommend", "Selected city: $city")
                                selectedCity = city
                            },
                            modifier = Modifier
                                .width(95.dp)
                                .padding(horizontal = 2.dp)
                                .background(color = MaterialTheme.colorScheme.primaryContainer, shape = MaterialTheme.shapes.medium),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedCity == city) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                                contentColor = if (selectedCity == city) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                city,
                                fontSize = 8.sp,
                                color = if (selectedCity == city) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                            )
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
                            userPreference.saveMedicalData(
                                viewModel.sensitivity,
                                viewModel.medHistory
                            )
                        }
                        Log.d("DialogRecommend", timeState.hour.toString())
                        onDismiss()
                    }) {
                        Text("Save")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = {
                        onDismiss()
                    }) {
                        Text("Cancel")
                    }
                }
            }
        }
    )
}