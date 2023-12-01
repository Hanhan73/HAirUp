package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.h_airup.HAirUpApp
import com.bangkit.h_airup.R
import com.bangkit.h_airup.model.Aqi
import com.bangkit.h_airup.ui.theme.HAirUpTheme

@Composable
fun ForecastItem(
    aqi: Aqi,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(color = color)
            .padding(8.dp)
    ) {
        // Date with two lines
        Column(
            modifier = Modifier
                .weight(0.4f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = aqi.date.substring(0, aqi.date.indexOf(',')),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                fontSize = 14.sp
            )
            Text(
                text = aqi.date.substring(aqi.date.indexOf(',') + 1).trim(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                fontSize = 14.sp
            )
        }

        // Spacer
        Spacer(modifier = Modifier.width(8.dp))

        // Status, AQI Level, Waving Hand
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {

            Text(
                text = aqi.status,
                maxLines = 2, // Allow up to 2 lines
                overflow = TextOverflow.Ellipsis, // Allow overflow to the next line
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
            )

            Text(
                text = aqi.aqiLvl.toString(),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(end = 16.dp)
            )

            // Waving Hand
            Image(
                painter = painterResource(id = R.drawable.hand_waving),
                contentDescription = "Waving Hand",
                modifier = Modifier
                    .size(24.dp)
                    .padding(8.dp)
            )
        }

        // Spacer
        Spacer(modifier = Modifier.width(8.dp))

        // Weather
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = Icons.Default.Add,
                contentDescription = aqi.weather,
                modifier = Modifier
                    .size(24.dp)
                    .padding(8.dp)
            )
            Text(
                text = aqi.weather,
                fontSize = 12.sp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ForecastItemPreview() {
    val aqi =         Aqi(
        "Friday, Nov 1",
        "Unhealthy for some groups",
        96,
        "Rainy"
    )
    HAirUpTheme {
        ForecastItem(
            aqi,
            color = Color.Yellow
        )
    }
}
