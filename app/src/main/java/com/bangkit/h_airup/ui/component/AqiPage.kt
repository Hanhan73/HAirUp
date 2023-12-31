package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bangkit.h_airup.response.PollutantsItem
import com.bangkit.h_airup.ui.theme.HAirUpTheme
import com.bangkit.h_airup.ui.theme.md_theme_light_onPrimaryContainer

@Composable
fun AqiPage(
    aqiNumber: Int?,
    aqiStatus: String,
    aqiPollutan: String?,
    pollutants: List<PollutantsItem?>?,
    modifier: Modifier = Modifier
) {
    var isDialogVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isDialogVisible = true }
        ) {
            // AQI Number
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(0.5f)
            ) {
                Text(
                    text = "AQI",
                    fontSize = 12.sp,
                    color = md_theme_light_onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = aqiNumber?.toString() ?: "-",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = md_theme_light_onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // AQI Status
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(0.7f)
            ) {
                Text(
                    text = "Status",
                    fontSize = 12.sp,
                    color = md_theme_light_onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = aqiStatus,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    color = md_theme_light_onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(0.5f)
            ) {
                Text(
                    text = "Main Pollutant",
                    fontSize = 12.sp,
                    color = md_theme_light_onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                val dominantPollutant = pollutants?.find { it?.code == aqiPollutan }

                if (dominantPollutant != null) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "${dominantPollutant.concentration?.value}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = md_theme_light_onPrimaryContainer
                        )
                        Text(
                            text = "${dominantPollutant.displayName}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = md_theme_light_onPrimaryContainer
                        )
                    }
                } else {
                    Text(
                        text = "N/A",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = md_theme_light_onPrimaryContainer
                    )
                }
            }
        }
    }

    if (isDialogVisible) {
        AQIDialog(
            onDismiss = { isDialogVisible = false },
            explanationText = "AQI EXPLAINATION"
        )
    }
}


@Composable
private fun AQIDialog(
    onDismiss: () -> Unit,
    explanationText: String
) {
    Dialog(
        onDismissRequest = { onDismiss() },
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "AQI Explanation",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = explanationText,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onDismiss() },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("Close")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AqiPagePreview() {
    HAirUpTheme {
        AqiPage(
            aqiNumber = 145,
            aqiStatus = "Kualitas Udara Sedang",
            aqiPollutan = "pm25",
            listOf()
        )
    }
}