package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.h_airup.ui.theme.md_theme_light_onTertiaryContainer
import com.bangkit.h_airup.ui.theme.md_theme_light_secondaryContainer
import com.bangkit.h_airup.ui.theme.md_theme_light_tertiaryContainer


@Composable
fun ButtonRecommend(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "There is no recommendation to show!",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Please fill the form first.",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onClick() },
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .background(
                        color = md_theme_light_secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(md_theme_light_tertiaryContainer),
                contentPadding = PaddingValues()
            ) {
                Text(
                    text = "Fill Form",
                    color =  md_theme_light_onTertiaryContainer,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun ButtonRecommendPreview() {
    ButtonRecommend({})
}