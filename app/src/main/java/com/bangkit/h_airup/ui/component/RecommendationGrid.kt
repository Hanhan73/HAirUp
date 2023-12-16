package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun RecommendationGrid(recommendations: List<String>) {
    if (recommendations.isNotEmpty()) {
        LazyColumn(
            userScrollEnabled = false
        ) {
            items(recommendations.chunked(2)) { rowRecommendations ->
                Row {
                    rowRecommendations.forEach { recommendation ->
                        RecommendationItem(recommendation)
                    }
                }
            }
        }
    } else {
        EmptyRecommendationMessage()
    }
}

@Composable
private fun RecommendationItem(
    recommend: String,
    modifier: Modifier = Modifier
) {
    // Use your custom row layout here
    // For simplicity, using a simple Text composable and Icons from Material3
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        when (recommend) {
            "Recommendation 1" -> Icon(Icons.Default.Info, contentDescription = null)
            "Recommendation 2" -> Icon(Icons.Default.Warning, contentDescription = null)
            "Recommendation 3" -> Icon(Icons.Default.Build, contentDescription = null)
            "Recommendation 4" -> Icon(Icons.Default.Done, contentDescription = null)
        }
        Text(
            text = recommend,
            modifier = Modifier.padding(8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun EmptyRecommendationMessage() {
    // Display a message when the list of recommendations is empty
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("There is no Recommendation History.")
    }
}

