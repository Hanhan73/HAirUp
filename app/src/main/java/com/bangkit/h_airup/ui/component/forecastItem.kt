package com.bangkit.h_airup.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.h_airup.R
import com.bangkit.h_airup.response.ForeCastAQIItem
import com.bangkit.h_airup.response.ForeCastWeatherItem
import com.bangkit.h_airup.utils.ColorPicker
import com.bangkit.h_airup.utils.IconPicker
import com.bangkit.h_airup.utils.categoryAqi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun forecastItem(
    forecast: ForeCastAQIItem?,
    modifier: Modifier = Modifier,
    daysAfter: Int,
    temp: Any?
) {
    val currentDate = LocalDate.now().plusDays(daysAfter.toLong())
    val dayOfWeek = currentDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val dateFormatted = currentDate.format(DateTimeFormatter.ofPattern("MMMM d"))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(color = ColorPicker.getAqiColor(forecast?.medianAQI))
            .padding(6.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(0.7f)
        ) {
            Text(
                text = dayOfWeek,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Normal
                ),
                fontSize = 12.sp,
                color = Color.Black,
            )
            Text(
                text = dateFormatted,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Normal
                ),
                fontSize = 12.sp,
                color = Color.Black,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = categoryAqi.getCategoryAqi(forecast?.medianAQI),
                maxLines = 4,
                overflow = TextOverflow.Visible,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Normal,
                ),
                fontSize = 12.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .weight(0.5f)
            )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(0.5f)
        ) {

            Text(
                text = forecast?.medianAQI.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(end = 8.dp)
            )

            Image(
                painter = painterResource(id = IconPicker.getAqiIcon(forecast?.medianAQI)),
                contentDescription = "Waving Hand",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.5f) // Adjusted weight for temperature
        ) {
            Image(
                painter = painterResource(id = R.drawable.temp),
                contentDescription = "Temperature",
                modifier = Modifier
                    .size(24.dp)
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "${temp}°C",
                fontSize = 10.sp,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Light
                ),
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun forecastAqiItem(
    forecast: ForeCastAQIItem?,
    modifier: Modifier = Modifier,
    daysAfter: Int,
) {
    val currentDate = LocalDate.now().plusDays(daysAfter.toLong())
    val dayOfWeek = currentDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val dateFormatted = currentDate.format(DateTimeFormatter.ofPattern("MMMM d"))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(color = ColorPicker.getAqiColor(forecast?.medianAQI))
            .padding(6.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(0.7f)
        ) {
            Text(
                text = dayOfWeek,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Normal
                ),
                fontSize = 12.sp,
                color = Color.Black,
            )
            Text(
                text = dateFormatted,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Normal
                ),
                fontSize = 12.sp,
                color = Color.Black,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = categoryAqi.getCategoryAqi(forecast?.medianAQI),
            maxLines = 4,
            overflow = TextOverflow.Visible,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Normal,
            ),
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(end = 8.dp, bottom = 8.dp)
                .weight(0.5f)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(0.5f)
        ) {

            Text(
                text = forecast?.medianAQI.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(end = 8.dp)
            )

            Image(
                painter = painterResource(id = IconPicker.getAqiIcon(forecast?.medianAQI)),
                contentDescription = "Waving Hand",
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.5f) // Adjusted weight for temperature
        ) {
            Image(
                painter = painterResource(id = R.drawable.d09),
                contentDescription = "Temperature",
                modifier = Modifier
                    .size(24.dp)
                    .padding(2.dp)
            )

            Text(
                text = forecast?.dominantPollutant.toString(),
                fontSize = 10.sp,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Light
                ),
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun forecastWeatherItem(
    forecast: ForeCastWeatherItem?,
    modifier: Modifier = Modifier,
    daysAfter: Int,

) {
    val currentDate = LocalDate.now().plusDays(daysAfter.toLong())
    val dayOfWeek = currentDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val dateFormatted = currentDate.format(DateTimeFormatter.ofPattern("MMMM d"))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(color = Color.White)
            .padding(6.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(0.4f)
        ) {
            Text(
                text = dayOfWeek,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Normal
                ),
                fontSize = 12.sp,
                color = Color.Black,
            )
            Text(
                text = dateFormatted,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Normal
                ),
                fontSize = 12.sp,
                color = Color.Black,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.5f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.temp),
                    contentDescription = "Temperature",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(2.dp)
                )

                Text(
                    text = "${forecast?.tx?.toInt()}°C",
                    maxLines = 2,
                    overflow = TextOverflow.Visible,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.5f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.humidity),
                    contentDescription = "Temperature",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(2.dp)
                )
                Text(
                    text = forecast?.rR.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
            }

        }

        Spacer(modifier = Modifier.width(8.dp))

    }
}


