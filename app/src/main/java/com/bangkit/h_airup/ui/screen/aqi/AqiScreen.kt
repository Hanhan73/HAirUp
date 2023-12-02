package com.bangkit.h_airup.ui.screen.aqi

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AqiScreen(

) {
    Text(
        text = "This is AQI Screen",
        modifier = Modifier.padding(64.dp))
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun AqiScreenPreview(){
    AqiScreen()
}
