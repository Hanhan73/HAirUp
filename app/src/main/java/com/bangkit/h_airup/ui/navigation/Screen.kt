package com.farhan.jetonepiece.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Aqi : Screen("favorite")
    object Weather : Screen("profile")
    object FormName : Screen("formName")
    object FormSensi : Screen("formSensi")
    object Welcome : Screen("welcome")

}