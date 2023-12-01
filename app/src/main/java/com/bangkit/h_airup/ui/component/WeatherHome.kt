package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun WeatherHome(
    temp: Int,
    status: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(190.dp)
            .height(75.dp)
            .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Weather Today",
                    fontSize = 8.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = temp.toString(),
                    fontSize = 28.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 8.dp, end = 8.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Account",
                    modifier = Modifier
                        .size(46.dp)
                        .padding(4.dp)
                )
                Text(
                    text = status,
                    fontSize = 8.sp,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp)
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
            145,
            "Cloudy"
        )
    }
}