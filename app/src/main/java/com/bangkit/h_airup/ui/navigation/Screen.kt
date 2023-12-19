package com.bangkit.h_airup.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Aqi : Screen("favorite")
    object Weather : Screen("weather")
    object FormName : Screen("formName")
    object FormSensi : Screen("formSensi")
    object Welcome : Screen("welcome")
    object Profile : Screen("profile")
    object EditProfile : Screen("editProfile")

}