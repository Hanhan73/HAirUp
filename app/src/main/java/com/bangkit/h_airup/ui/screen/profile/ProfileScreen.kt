package com.bangkit.h_airup.ui.screen.profile

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bangkit.h_airup.R
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.pref.UserModel
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.ui.theme.md_theme_light_onPrimaryContainer
import com.bangkit.h_airup.ui.theme.md_theme_light_primaryContainer
import com.bangkit.h_airup.ui.theme.md_theme_light_tertiary
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
    val userModel by userPreference.getSession().collectAsState(initial = UserModel())

    var isEditMode by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
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
            color = md_theme_light_tertiary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(250.dp).align(Alignment.CenterHorizontally)

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
            .background(color = md_theme_light_primaryContainer)
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
        Text(text = "$label:", style = MaterialTheme.typography.bodySmall, color = md_theme_light_onPrimaryContainer)
        Text(text = value, style = MaterialTheme.typography.bodySmall, color = md_theme_light_onPrimaryContainer)
    }
}