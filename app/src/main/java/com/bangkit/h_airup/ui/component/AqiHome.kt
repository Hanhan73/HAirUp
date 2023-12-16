package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.h_airup.R
import com.bangkit.h_airup.ui.theme.HAirUpTheme
import com.bangkit.h_airup.utils.ColorPicker

@Composable
fun AqiHome(
    aqiNumber: Int?,
    aqiStatus: String,
    modifier: Modifier = Modifier
) {


    Box(
        modifier = modifier
            .width(150.dp)
            .height(130.dp)
            .background(color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.9f))
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Air Quality Index (AQI)",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = aqiNumber.toString(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = ColorPicker.getAqiColor(aqiNumber),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = aqiStatus,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AqiHomePreview() {
    HAirUpTheme {
        AqiHome(
            145,
            "Unhealthy"
        )
    }
}