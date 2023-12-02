package com.bangkit.h_airup.ui.navigation

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.farhan.jetonepiece.ui.navigation.Screen


data class NavigationItem(
    val title: String,
    val icon: Painter,
    val screen: Screen
)