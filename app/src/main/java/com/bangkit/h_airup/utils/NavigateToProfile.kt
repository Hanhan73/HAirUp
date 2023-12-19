package com.bangkit.h_airup.utils

import androidx.navigation.NavController
import com.bangkit.h_airup.ui.navigation.Screen

fun onProfileItemClick(navController: NavController) {
    navController.navigate(Screen.Profile.route)
}
fun onEditProfileItemClick(navController: NavController) {
    navController.navigate(Screen.EditProfile.route)
}


