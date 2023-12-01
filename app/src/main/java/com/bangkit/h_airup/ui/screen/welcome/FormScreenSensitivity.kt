package com.bangkit.h_airup.ui.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FormScreenSensitivity(
    modifier: Modifier = Modifier
) {
    FormScreenSensitivityContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreenSensitivityContent() {
    var expanded by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    val cities = listOf("Bandung", "Malang", "Jakarta")

    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = Icons.Default.Clear,
            contentDescription = "Logo",
            modifier = Modifier
                .size(250.dp)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Fill in the form",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Location Box with border and label
        Box(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                .padding(vertical = 16.dp, horizontal = 12.dp)
                .clickable { expanded = !expanded }
        ) {
            Text(
                text = if (location.isBlank()) "Select Sensitivity" else location,
                color = if (location.isBlank()) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface
            )
        }

        // DropdownMenu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            cities.forEach { city ->
                DropdownMenuItem( text = { Text(text = city) },
                    onClick = {
                        location = city
                        expanded = false
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                .padding(vertical = 16.dp, horizontal = 12.dp)
                .clickable { expanded = !expanded }
        ) {
            Text(
                text = if (location.isBlank()) "Select Your Medical History" else location,
                color = if (location.isBlank()) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface
            )
        }

        // DropdownMenu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            cities.forEach { city ->
                DropdownMenuItem( text = { Text(text = city) },
                    onClick = {
                        location = city
                        expanded = false
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))



        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),

        ) {
            Text(
                text = "Finish",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("Skip For Now")
                }
            },
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraLight,
            ),
            fontSize = 10.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)

        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = if (index == 2) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                            },
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun FormScreenSensitivityPreview() {
    FormScreenSensitivityContent()
}