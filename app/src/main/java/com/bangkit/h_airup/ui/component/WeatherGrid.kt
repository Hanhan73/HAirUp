package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.h_airup.ui.theme.md_theme_light_onPrimaryContainer
import com.bangkit.h_airup.ui.theme.md_theme_light_primaryContainer

@Composable
fun WeatherGridItem(title: String, text: String, iconRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = md_theme_light_primaryContainer.copy(alpha = 0.7f),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = md_theme_light_onPrimaryContainer
        )
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .aspectRatio(1f)
                .padding(8.dp)
        )
        Text(
            text = text,
            fontSize = 16.sp,
            color = md_theme_light_onPrimaryContainer
        )
    }
}


