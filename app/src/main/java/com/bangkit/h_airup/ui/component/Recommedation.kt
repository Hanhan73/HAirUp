package com.bangkit.h_airup.ui.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.ui.screen.welcome.FormViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun Recommendation(
    modifier: Modifier = Modifier,
    userPreference: UserPreference,
    viewModel: FormViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current), LocalContext.current)
    )
) {
    val pagerState = rememberPagerState(
        pageCount = if (userPreference.getStatus() != null) 4 else 3, // Adjust the number of tabs based on the condition
        initialPage = 0
    )

    val recommendations = listOf(
        "Recommendation 1",
        "Recommendation 2",
        "Recommendation 3",
        "Recommendation 4"
    )

    var showDialog by remember { mutableStateOf(false) }
    var shouldAnimate by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
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
    }

    Column(
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) { page ->
            when (page) {
                0 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        RecommendationGrid(recommendations = recommendations)
//
//
//                        Button(
//                            onClick = { showDialog = true },
//                            modifier = Modifier
//                                .height(56.dp)
//                                .fillMaxWidth()
//                                .background(MaterialTheme.colorScheme.primary)
//                                .padding(horizontal = 16.dp),
//                            contentPadding = PaddingValues()
//                        ) {
//                            Text(
//                                text = "Recommend Again",
//                                color = Color.White,
//                            )
//                        }
                    }
                    if (showDialog) {
                        DialogRecommend(
                            onDismiss = { showDialog = false },
                            userPreference = userPreference
                        )
                    }
                }
                1 ->
                    if (pagerState.currentPage == 3) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            RecommendationGrid(recommendations = recommendations)
                        }
                    } else {
                        ButtonRecommend { showDialog = true }
                    }

                2 -> RecommendationGrid(recommendations = listOf())
                3 -> RecommendationGrid(recommendations = listOf())
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



@Composable
fun DialogRecommend(
    onDismiss: () -> Unit,
    userPreference: UserPreference,
    viewModel: FormViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current), LocalContext.current)
    )
) {
    val coroutineScope = rememberCoroutineScope()
    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }

    val sensitivities = listOf("asdadf", "sdds", "sdsadsd")
    val medHistories = listOf("ASADAD", "ADFDF", "ADSFE")

    Dialog(
        onDismissRequest = { onDismiss() },
        content = {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Fill The dropdown",
                    modifier = Modifier.padding(16.dp)
                )
                DropdownField(
                    label = "Sensitivity",
                    value = viewModel.sensitivity,
                    expanded = expanded1,
                    items = sensitivities,
                    onValueChange = { newValue ->
                        viewModel.sensitivity = newValue
                    },
                    onExpand = { expanded1 = true },
                    onClose = { expanded1 = false },
                )

                Spacer(modifier = Modifier.height(16.dp))

                DropdownField(
                    label = "Medical History",
                    value = viewModel.medHistory,
                    expanded = expanded2,
                    items = medHistories,
                    onValueChange = { newValue ->
                        viewModel.medHistory = newValue
                    },
                    onExpand = { expanded2 = true },
                    onClose = { expanded2 = false },
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        coroutineScope.launch {
                            userPreference.saveMedicalData(
                                viewModel.sensitivity,
                                viewModel.medHistory
                            )
                        }
                        onDismiss()
                    }) {
                        Text("Save")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = {
                        onDismiss()
                    }) {
                        Text("Cancel")
                    }
                }
            }
        }
    )
}


@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun DialogFormPreview() {
    val userPreference = UserPreference.getInstance(context = LocalContext.current)
    Recommendation(userPreference = userPreference)
}