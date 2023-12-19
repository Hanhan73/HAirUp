package com.bangkit.h_airup.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bangkit.h_airup.R
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.pref.UserModel
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.utils.onEditProfileItemClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userPreference: UserPreference,
    viewModel: ProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current), LocalContext.current)
    ),
    navController: NavController
) {
    // Collect the current user data
    val userModel by userPreference.getSession().collectAsState(initial = UserModel())

    // State to determine whether the profile is in "edit" mode
    var isEditMode by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar with Back button and Title
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
                    onEditProfileItemClick(navController)
                    isEditMode = true
                }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
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

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(250.dp)
                .padding(8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary) // Set the image background color
                .align(Alignment.CenterHorizontally) // Center the image within its container
        )


        Spacer(modifier = Modifier.height(16.dp))

        UserInfo(
            userModel = userModel,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun UserInfo(
    userModel: UserModel,

) {
    Box(
        Modifier.padding(16.dp)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 32.dp)
        ) {
            UserInfoItem("Name", userModel.name)
            Spacer(modifier = Modifier.height(16.dp))
            UserInfoItem("Age", userModel.age.toString())
            Spacer(modifier = Modifier.height(16.dp))
            UserInfoItem("Location", userModel.citygps ?: userModel.city)
            Spacer(modifier = Modifier.height(16.dp))
            UserInfoItem("Status", userModel.sensitivity)
            Spacer(modifier = Modifier.height(16.dp))
            UserInfoItem("Medical History", userModel.medHistory)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun UserInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", style = MaterialTheme.typography.bodySmall)
        Text(text = value, style = MaterialTheme.typography.bodySmall)
    }
}