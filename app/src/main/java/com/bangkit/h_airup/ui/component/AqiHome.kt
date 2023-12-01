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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.h_airup.R
import com.bangkit.h_airup.ui.theme.HAirUpTheme

@Composable
fun AqiHome (
    aqiNumber : Int,
    aqiStatus : String,
    modifier: Modifier = Modifier
    ){

    val textColor = getAqiColor(aqiNumber)

    Box(
        modifier = modifier
            .width(150.dp)
            .height(130.dp)
            .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, top = 8.dp)
        ) {
            Text(
                text = "Air Quality Index (AQI)",
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = aqiNumber.toString(),
                fontSize = 48.sp,
                color = textColor,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = aqiStatus,
                fontSize = 16.sp,
            )

        }
    }
}

@Composable
private fun getAqiColor(aqiNumber: Int): Color {
    return when {
        aqiNumber in 0..50 -> Color.Blue
        aqiNumber in 51..100 -> Color.Green
        aqiNumber in 101..150 -> Color.Yellow
        aqiNumber in 151..200 -> Color.Red
        else -> Color.Magenta
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