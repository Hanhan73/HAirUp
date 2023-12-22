package com.bangkit.h_airup.ui.screen.welcome

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bangkit.h_airup.R
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.model.LocationData
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.ui.component.DropdownField
import com.bangkit.h_airup.ui.navigation.Screen
import com.bangkit.h_airup.ui.theme.HAirUpTheme
import com.bangkit.h_airup.ui.theme.md_theme_light_secondaryContainer
import kotlinx.coroutines.launch

@Composable
fun FormScreenName(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FormViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current), LocalContext.current)
    ),
) {

    FormScreenContent(navController = navController, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreenContent(navController: NavController, viewModel: FormViewModel = viewModel(
    factory = ViewModelFactory(Injection.provideRepository(LocalContext.current), LocalContext.current)
)) {
    var expandedCity by remember { mutableStateOf(false)}
    var expandedProvince by remember { mutableStateOf(false)}
    val userPreference = UserPreference.getInstance(LocalContext.current)
    val coroutineScope = rememberCoroutineScope()
    var provinceGps = ""
    var cityGps = ""
    LaunchedEffect(UserPreference) {
        provinceGps = userPreference.getProvince()
        cityGps = userPreference.getCity()
    }

    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(250.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Fill in the form",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            TextField(
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 12.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = md_theme_light_secondaryContainer,
                )
            )
        }

        item {

            Row {
                DropdownField(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(start = 8.dp),
                    label = "Province",
                    value = viewModel.province,
                    expanded = expandedProvince,
                    items = LocationData.locationData.map { it.province }.distinct(),
                    onValueChange = { selectedProvince ->
                        viewModel.province = selectedProvince
                        viewModel.filteredCities = LocationData.locationData
                            .filter { it.province == selectedProvince }
                            .flatMap { it.city }
                            .distinct()
                        viewModel.city = ""
                    },
                    onExpand = { expandedProvince = true },
                    onClose = { expandedProvince = false }
                )
                Spacer(modifier = Modifier.width(8.dp))

                DropdownField(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(200.dp),
                    label = "City",
                    value = viewModel.city,
                    expanded = expandedCity,
                    items = viewModel.filteredCities,
                    onValueChange = { selectedCity ->
                        viewModel.city = selectedCity
                    },
                    onExpand = { expandedCity = true },
                    onClose = { expandedCity = false }
                )

            }
        }


        item {
            TextField(
                value = viewModel.age,
                onValueChange = { viewModel.age = it },
                label = { Text("Age") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 12.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = md_theme_light_secondaryContainer,
                )
            )
        }

        item {
            Button(
                onClick = {
                    coroutineScope.launch {
                        userPreference.saveUserData(
                            viewModel.name,
                            viewModel.city,
                            viewModel.province,
                            viewModel.age.toInt()
                        )


                        navController.navigate(Screen.FormSensi.route)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text(
                    text = "Next",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                color = if (index == 1) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                                },
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FormScreenPreview() {
    HAirUpTheme {
        FormScreenContent(rememberNavController())
    }
}