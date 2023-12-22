package com.bangkit.h_airup.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.h_airup.R
import com.bangkit.h_airup.ui.theme.md_theme_light_tertiary


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
        Text(
            text = "Today's Recommendation",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold,
            ),
            color = md_theme_light_tertiary,
            textAlign = TextAlign.Justify,
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = recommend,
            modifier = Modifier
                .padding(8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = md_theme_light_tertiary
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        ) {
            item {
                RecommendIconGrid(
                    text = "Nyalakan Penyaring Udara",
                    iconRes = R.drawable.air_purifier,
                )
            }
            item {
                RecommendIconGrid(
                    text = "Kurangi Aktivitas Outdoor",
                    iconRes = R.drawable.do_not_go_out,
                )
            }
            item {
                RecommendIconGrid(
                    text = "Sebaiknya Menggunakan Masker",
                    iconRes = R.drawable.medical_mask,
                )
            }
            item {
                RecommendIconGrid(
                    text = "Tutup Jendela Anda",
                    iconRes = R.drawable.window,
                )
            }
        }
    }
}

@Composable
private fun EmptyRecommendationMessage() {
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

