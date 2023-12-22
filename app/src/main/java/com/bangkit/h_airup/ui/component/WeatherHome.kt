package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.h_airup.ui.theme.HAirUpTheme
import com.bangkit.h_airup.ui.theme.md_theme_light_onPrimaryContainer
import com.bangkit.h_airup.ui.theme.md_theme_light_primary
import com.bangkit.h_airup.ui.theme.md_theme_light_primaryContainer
import com.bangkit.h_airup.utils.IconPicker

@Composable
fun WeatherHome(
    temp: Int,
    status: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(190.dp)
            .height(90.dp)
            .background(color = md_theme_light_primaryContainer.copy(alpha = 0.7f))
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = "Weather",
                    fontSize = 10.sp,
                    color = md_theme_light_primary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "${temp}°C",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = md_theme_light_onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Image(
                    painter = painterResource(id = IconPicker.getWeatherIcon(icon)),
                    contentDescription = "weather status",
                    modifier = Modifier
                        .size(46.dp)
                        .padding(4.dp)
                )
                Text(
                    text = status,
                    fontSize = 10.sp,
                    color = md_theme_light_onPrimaryContainer,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun WeatherHomePreview() {
    HAirUpTheme {
        WeatherHome(
            temp = 22,
            status = "Cloudy",
            icon = "01d"
        )
    }
}