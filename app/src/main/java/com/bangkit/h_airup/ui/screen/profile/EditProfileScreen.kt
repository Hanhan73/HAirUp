package com.bangkit.h_airup.ui.screen.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bangkit.h_airup.R
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.model.LocationData
import com.bangkit.h_airup.model.UserRequestBody
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.ui.component.DropdownField
import com.bangkit.h_airup.ui.theme.md_theme_light_secondaryContainer
import com.bangkit.h_airup.utils.onProfileItemClick
import com.bangkit.h_airup.utils.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    userPreference: UserPreference,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(LocalContext.current),
            LocalContext.current
        )
    ),
    navController: NavController
) {

    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expandedCity by remember { mutableStateOf(false) }
    var expandedProvince by remember { mutableStateOf(false) }

    val status = listOf("Pregnant", "Athletes")
    val medHistories = listOf("Heart Disease", "Lung Disease")
    val userPreference = UserPreference.getInstance(LocalContext.current)
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            selectedImageUri = it
        })


    LaunchedEffect(key1 = viewModel) {
        viewModel.initializeUserData(userPreference)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            userPreference.saveUserData(
                                viewModel.name,
                                viewModel.city,
                                viewModel.province,
                                viewModel.age.toInt()
                            )
                            userPreference.saveMedicalData(
                                viewModel.sensitivity,
                                viewModel.medHistory
                            )
                            val selectedImageFile = selectedImageUri?.let { uriToFile(context, it) }

                            Log.d("EditProfile", selectedImageFile?.path.toString())
                            val requestBody = UserRequestBody(
                                nama = viewModel.name,
                                umur = viewModel.age.toInt(),
                                lokasi = userPreference.getCity(),
                                files = selectedImageFile,
                                status = viewModel.sensitivity,
                                riwayatPenyakit = viewModel.medHistory
                            )
                            val userId = userPreference.getUserId()

                            val file = File(selectedImageUri.toString())

                            val reqFile = selectedImageFile?.let {
                                RequestBody.create("image/*".toMediaTypeOrNull(),
                                    it
                                )
                            }
                            val body = reqFile?.let {
                                MultipartBody.Part.createFormData("upload", file.name,
                                    it
                                )
                            }

                            val response = body?.let { viewModel.putUser(it, requestBody, userId) }

                            Log.d("EditProfile", response.toString())
                        }

                        onProfileItemClick(navController)
                    }) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    }
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.profile_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary // Set the text color
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .clickable {
                        pickImageLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
            ) {
                if (selectedImageUri != null) {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(250.dp)
                            .clip(CircleShape)
                            .align(Alignment.Center)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(350.dp)
                            .align(Alignment.Center)

                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                item {
                    TextField(
                        value = viewModel.name,
                        onValueChange = { viewModel.name = it },
                        label = { Text("Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 12.dp)
                            .background(
                                color = md_theme_light_secondaryContainer,
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
                                .width(150.dp)
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
                                color = md_theme_light_secondaryContainer,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = md_theme_light_secondaryContainer,
                        )
                    )
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.94f)
                            .background(md_theme_light_secondaryContainer)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.shapes.small
                            )
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
                                color = if (viewModel.sensitivity.isBlank()) MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.6f
                                ) else MaterialTheme.colorScheme.onSurface
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
                }

                item {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.94f)
                            .background(md_theme_light_secondaryContainer)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.shapes.small
                            )
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
                                color = if (viewModel.medHistory.isBlank()) MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.6f
                                ) else MaterialTheme.colorScheme.onSurface
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
                }
            }
        }
    }
}


