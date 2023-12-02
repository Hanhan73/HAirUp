package com.bangkit.h_airup.ui.screen.weather

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeatherScreen(

) {
    Text(
        text = "This is Weather Screen",
        modifier = Modifier.padding(64.dp))
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun WeatherScreenPreview(){
    WeatherScreen()
}
