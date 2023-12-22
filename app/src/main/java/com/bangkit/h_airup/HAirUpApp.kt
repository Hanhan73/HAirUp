@file:OptIn(ExperimentalMaterial3Api::class)

package com.bangkit.h_airup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bangkit.h_airup.ui.navigation.NavigationItem
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.navigation.Screen
import com.bangkit.h_airup.ui.screen.aqi.AqiScreen
import com.bangkit.h_airup.ui.screen.home.HomeScreen
import com.bangkit.h_airup.ui.screen.profile.EditProfileScreen
import com.bangkit.h_airup.ui.screen.profile.ProfileScreen
import com.bangkit.h_airup.ui.screen.weather.WeatherScreen
import com.bangkit.h_airup.ui.screen.welcome.FormScreenName
import com.bangkit.h_airup.ui.screen.welcome.FormScreenSensitivity
import com.bangkit.h_airup.ui.screen.welcome.WelcomeScreen
import com.bangkit.h_airup.ui.theme.HAirUpTheme
import com.bangkit.h_airup.ui.theme.md_theme_light_inverseOnSurface
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HAirUpApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val userPreference = UserPreference.getInstance(LocalContext.current)
    LaunchedEffect(key1 = userPreference) {
        val isFirstTime = userPreference.getSession()
            .map { it.isFirstTime }
            .first()

        if (isFirstTime) {
            navController.navigate(Screen.Welcome.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                restoreState = true
            }
        }
    }


    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Welcome.route
                && currentRoute != Screen.FormName.route
                && currentRoute != Screen.FormSensi.route
                && currentRoute != Screen.Profile.route
                && currentRoute != Screen.EditProfile.route
                ) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    userPreference = UserPreference.getInstance(LocalContext.current),
                    navController = navController
                )

            }
            composable(Screen.Aqi.route) {
                AqiScreen(
                    userPreference = UserPreference.getInstance(LocalContext.current),
                    navController = navController
                )
            }
            composable(Screen.Weather.route) {
                WeatherScreen(
                    userPreference = UserPreference.getInstance(LocalContext.current),
                    navController = navController
                )
            }
            composable(Screen.Profile.route){
                ProfileScreen(
                    userPreference = UserPreference.getInstance(LocalContext.current),
                    navController = navController

                )
            }
            composable(Screen.EditProfile.route){
                EditProfileScreen(
                    userPreference = UserPreference.getInstance(LocalContext.current),
                    navController = navController

                )
            }
            composable(Screen.FormName.route) {
                FormScreenName(
                    navController,

                )
            }
            composable(Screen.FormSensi.route) {
                FormScreenSensitivity(navController)
            }
            composable(Screen.Welcome.route) {
                WelcomeScreen(navController = navController)
            }
        }
    }
}


@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavigationBar(
        modifier = modifier,
        containerColor = md_theme_light_inverseOnSurface
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon =  painterResource(id = R.drawable.home_icon),
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_aqi),
                icon = painterResource(id = R.drawable.aqi_icon),
                screen = Screen.Aqi
            ),
            NavigationItem(
                title = stringResource(R.string.menu_weather),
                icon = painterResource(id = R.drawable.weather_icon),
                screen = Screen.Weather
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = item.title)
                       },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun HAirUpPreview(){
    HAirUpTheme {
        HAirUpApp()
    }
}