package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.bangkit.h_airup.response.CurrentDataAQI
import com.bangkit.h_airup.response.PastDataAQIItem
import com.bangkit.h_airup.utils.ColorPicker

@Composable
fun currentForecastItem(
    curr: CurrentDataAQI?,
    modifier: Modifier = Modifier
) {


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(color = ColorPicker.getAqiColor(curr?.aqi))
            .padding(6.dp)
    ) {
//        // Date with two lines
//        Column(
//            modifier = Modifier
//                .weight(0.4f)
//        ) {
//            Text(
//                text = aqi.date.substring(0, aqi.date.indexOf(',')),
//                style = MaterialTheme.typography.headlineSmall.copy(
//                    fontWeight = FontWeight.Normal
//                ),
//                fontSize = 12.sp
//            )
//            Text(
//                text = aqi.date.substring(aqi.date.indexOf(',') + 1).trim(),
//                style = MaterialTheme.typography.headlineSmall.copy(
//                    fontWeight = FontWeight.Normal
//                ),
//                fontSize = 12.sp
//            )
//        }

        // Spacer
        Spacer(modifier = Modifier.width(8.dp))

        // Status, AQI Level, Waving Hand
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Current",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Normal
                ),
                color = Color.Black,
                fontSize = 12.sp

            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = curr?.category.toString(),
                maxLines = 2,
                overflow = TextOverflow.Visible,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Normal,
                ),
                fontSize = 12.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
            )

            Text(
                text = curr?.aqiDisplay.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(end = 8.dp)
            )

            // Waving Hand
            Image(
                painter = painterResource(id = R.drawable.n01),
                contentDescription = "Waving Hand",
                modifier = Modifier
                    .size(28.dp)
                    .padding(8.dp)
            )
        }

        // Spacer
        Spacer(modifier = Modifier.width(8.dp))

//        // Weather
//        Column(
//            verticalArrangement = Arrangement.Top,
//            modifier = Modifier.fillMaxHeight()
//        ) {
//            Image(
//                imageVector = Icons.Default.Add,
//                contentDescription = aqi.weather,
//                modifier = Modifier
//                    .size(24.dp)
//                    .padding(2.dp)
//            )
//            Text(
//                text = aqi.weather,
//                fontSize = 10.sp,
//                style = MaterialTheme.typography.bodySmall.copy(
//                    fontWeight = FontWeight.Light
//                ),
//            )
//        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun ForecastItemPreview() {
//    val aqi =         Aqi(
//        "Wednesday, Nov 1",
//        "Unhealthy for some groups",
//        96,
//        "Rainy"
//    )
//    HAirUpTheme {
//        ForecastItem(
//            aqi,
//        )
//    }
//}
