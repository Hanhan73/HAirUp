package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.bangkit.h_airup.R


@Composable
fun RecommendationGrid(recommendations: String) {
    if (recommendations != ""){
    LazyColumn(
            userScrollEnabled = false
        ) {
            item {

            RecommendationItem(recommendations)
            }
        }
    }
    else {
        EmptyRecommendationMessage()
    }
}

@Composable
private fun RecommendationItem(
    recommend: String,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Component Name
        Text(
            text = recommend,
            modifier = Modifier.padding(8.dp),
            fontSize = 8.sp,
            fontWeight = FontWeight.Normal
        )

        // Icons Row
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.height(200.dp),
                userScrollEnabled = false) {
                item {
                    RecommendIconGrid(
                        text = "Text 1",
                        iconRes = R.drawable.weather_icon
                    )
                }
                item {
                    RecommendIconGrid(
                        text = "Text 2",
                        iconRes = R.drawable.d02
                    )
                }
                item {
                    RecommendIconGrid(
                        text = "Text 3",
                        iconRes = R.drawable.d02
                    )
                }
                item {
                    RecommendIconGrid(
                        text = "Text 4",
                        iconRes = R.drawable.d11
                    )
                }
            }
        }
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

