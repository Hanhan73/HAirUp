package com.bangkit.h_airup.ui.screen.welcome

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bangkit.h_airup.R
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.model.UserRequestBody
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.ui.navigation.Screen
import com.bangkit.h_airup.ui.theme.md_theme_light_onSecondaryContainer
import com.bangkit.h_airup.ui.theme.md_theme_light_secondaryContainer
import kotlinx.coroutines.launch

@Composable
fun FormScreenSensitivity(
    navController: NavController,
    viewModel: FormViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current), LocalContext.current)
    ),
) {
    FormScreenSensitivityContent(navController, viewModel)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FormScreenSensitivityContent(
    navController: NavController,
    viewModel: FormViewModel
) {
    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }

    val userPreference = UserPreference
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val status = listOf("Pregnant", "Athletes")
    val medHistories = listOf("Heart Disease", "Lung Disease")

    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .clickable {
                keyboardController?.hide()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .background(md_theme_light_secondaryContainer)
                .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                .padding(vertical = 16.dp, horizontal = 12.dp)
                .clickable { expanded1 = !expanded1 }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (viewModel.sensitivity.isBlank()) "Select Status" else viewModel.sensitivity,
                    color = if (viewModel.sensitivity.isBlank()) md_theme_light_onSecondaryContainer.copy(alpha = 0.6f) else md_theme_light_onSecondaryContainer
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

            DropdownMenu(
                expanded = expanded1,
                onDismissRequest = { expanded1 = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                status.forEach { sensi ->
                    DropdownMenuItem(
                        text = { Text(text = sensi) },
                        onClick = {
                            viewModel.sensitivity = sensi
                            expanded1 = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .background(md_theme_light_secondaryContainer)
                .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                .padding(vertical = 16.dp, horizontal = 12.dp)
                .clickable { expanded2 = !expanded2 }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (viewModel.medHistory.isBlank()) "Select Medical History" else viewModel.medHistory,
                    color = if (viewModel.medHistory.isBlank()) md_theme_light_onSecondaryContainer.copy(alpha = 0.6f) else md_theme_light_onSecondaryContainer
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

            DropdownMenu(
                expanded = expanded2,
                onDismissRequest = { expanded2 = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                medHistories.forEach { med ->
                    DropdownMenuItem(
                        text = { Text(text = med) },
                        onClick = {
                            viewModel.medHistory = med
                            expanded2 = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))



        Button(
            onClick = {
                coroutineScope.launch {
                    val requestBody = UserRequestBody(
                        nama = userPreference.getInstance(context).getName(),
                        umur = userPreference.getInstance(context).getAge(),
                        lokasi = if (userPreference.getInstance(context).getCity() == null) "Kab. Bandung Barat" else userPreference.getInstance(context).getCity(),
                        status = if (viewModel.sensitivity.isBlank()) null else viewModel.sensitivity,
                        riwayatPenyakit = if (viewModel.medHistory.isBlank()) null else viewModel.medHistory,
                        files = null
                    )

                    val userId = viewModel.postUser(requestBody)
                    Log.d("FormScreen", userId.toString())

                    if (!userId.isNullOrBlank()) {
                        userPreference.getInstance(context).saveMedicalData(viewModel.sensitivity, viewModel.medHistory)
                        userPreference.getInstance(context).setIsFirstTime(false)
                        userPreference.getInstance(context).setUserId(userId)

                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
        ) {
            Text(
                text = "Finish",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("Skip For Now")
                }
            },
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraLight,
            ),
            fontSize = 10.sp,
            modifier = Modifier
                .clickable {
                    coroutineScope.launch {
                        val requestBody = UserRequestBody(
                            nama = userPreference.getInstance(context).getName(),
                            umur = userPreference.getInstance(context).getAge(),
                            lokasi = if (userPreference.getInstance(context).getCity() == null) "Kab. Bandung Barat" else userPreference.getInstance(context).getCity(),
                            status = null,
                            riwayatPenyakit = null,
                            files = null
                        )

                        val userId = viewModel.postUser(requestBody)
                        Log.d("FormScreen", userId.toString())

                        if (!userId.isNullOrBlank()) {
                            userPreference.getInstance(context).saveMedicalData(viewModel.sensitivity, viewModel.medHistory)
                            userPreference.getInstance(context).setIsFirstTime(false)
                            userPreference.getInstance(context).setUserId(userId)
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) {
                                    inclusive = true
                                }
                            }
                        } else {
                        }
                    }
                }
                .padding(top = 8.dp, bottom = 32.dp)

        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = if (index == 2) {
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
