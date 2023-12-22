package com.bangkit.h_airup.ui.component

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.model.RecommendRequestBody
import com.bangkit.h_airup.pref.SharedPreferencesManager
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.ui.screen.home.HomeViewModel
import com.bangkit.h_airup.ui.theme.md_theme_light_secondaryContainer
import com.bangkit.h_airup.ui.theme.md_theme_light_tertiary
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Recommendation(
    modifier: Modifier = Modifier,
    userPreference: UserPreference,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current), LocalContext.current)
    ),
    generalRecommend: String?,
    rekomendasi: String?,
    sharedPreferencesManager: SharedPreferencesManager
) {
    val pagerState = rememberPagerState(
        pageCount = calculatePageCount(userPreference, sharedPreferencesManager.getRekomen()),
        initialPage = 0
    )

    var showDialog by remember { mutableStateOf(false) }
    var rekomen by remember { mutableStateOf(sharedPreferencesManager.getRekomen() ?: "") }
    var shouldAnimate by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            while (true) {
                yield()
                delay(6000)
                if (shouldAnimate) {
                    pagerState.animateScrollToPage(
                        page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                        animationSpec = tween(600)
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Column(
        modifier = modifier,

    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
        ) { page ->
            when (page) {
                0 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        RecommendationGrid(recommendations = generalRecommend.toString())
                    }
                }
                1 ->
                    if (pagerState.currentPage == 3) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            RecommendationGrid(recommendations = generalRecommend.toString())
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = modifier
                        ) {
                            Text(
                                text = "Recommendation For You",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                ),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(8.dp),
                                color = md_theme_light_tertiary
                            )
                            Text(
                                text = rekomendasi.toString(),
                                modifier = Modifier
                                    .padding(8.dp),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = md_theme_light_tertiary
                            )
                        }
                    }
                2 -> ButtonRecommend { showDialog = true }
                3 -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                ) {
                    Text(
                        text = "Personalize Recommendation",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                        ),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = rekomen,
                        modifier = Modifier
                            .padding(8.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        if (showDialog) {
            Box(modifier = Modifier.background(md_theme_light_secondaryContainer)){

                DialogRecommend(
                    onDismiss = {
                        showDialog = false
                    },
                    userPreference = userPreference,
                    viewModel = viewModel,
                    recommendationCallback = object : RecommendationCallback {
                        override fun onRecommendationDataReceived(data: String?) {
                            rekomen = data.toString()
                            sharedPreferencesManager.saveRekomen(rekomen)
                            pagerState.pageCount = calculatePageCount(userPreference, rekomen)
                        }
                    }
                )
            }

            Log.d("Rekomendasi", rekomen)

            LaunchedEffect(key1 = viewModel) {
                val userId = userPreference.getUserId()

                val requestBody = RecommendRequestBody(
                    viewModel.hour,
                    viewModel.day,
                    viewModel.kota
                )

                Log.d("Recommendation", requestBody.toString())
                viewModel.postRecommendResponse(requestBody, userId)
                viewModel.getRecommendResponse(userId)
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}

private fun calculatePageCount(userPreference: UserPreference, rekomen: String?): Int {
    return if (userPreference.getStatus() != null && !rekomen.isNullOrBlank()) {
        4
    } else {
        3
    }
}

interface RecommendationCallback {
    fun onRecommendationDataReceived(data: String?)
}