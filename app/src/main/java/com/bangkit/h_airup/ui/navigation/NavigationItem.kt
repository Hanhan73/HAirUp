package com.bangkit.h_airup.ui.navigation

import androidx.compose.ui.graphics.painter.Painter


data class NavigationItem(
    val title: String,
    val icon: Painter,
    val screen: Screen
)