package com.farhan.jetonepiece.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Aqi : Screen("favorite")
    object Weather : Screen("profile")
}