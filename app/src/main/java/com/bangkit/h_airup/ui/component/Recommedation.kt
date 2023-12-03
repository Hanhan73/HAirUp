package com.bangkit.h_airup.ui.component

import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.h_airup.di.Injection
import com.bangkit.h_airup.pref.UserModel
import com.bangkit.h_airup.pref.UserPreference
import com.bangkit.h_airup.ui.ViewModelFactory
import com.bangkit.h_airup.ui.screen.welcome.FormViewModel
import com.farhan.jetonepiece.ui.navigation.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Recommendation(
    modifier: Modifier = Modifier,
    userPreference: UserPreference,
    viewModel: FormViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {

    val pagerState = rememberPagerState(
        pageCount = 3,
        initialPage = 0
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
            val content = when (page) {
                0 -> {
                    if (viewModel.sensitivity.isNotEmpty() && viewModel.medHistory.isNotEmpty()) {
                        listOf(
                            "Type 1 Recommendation A",
                            "Type 1 Recommendation B",
                            "Type 1 Recommendation C"
                        )
                    } else {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Button(
                                onClick = {
                                    showDialog = true
                                    // Set shouldAnimate to false when the button is pressed
                                    shouldAnimate = false
                                },
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                                Text("Fill Sensitivity and Medical History")
                            }
                            Text("Fill Sensitivity and Medical History")
                        }
                        if (showDialog) {
                            // Show the FillFormDialog as a part of your Compose UI hierarchy
                            FillFormDialog(
                                onDismiss = {
                                    showDialog = false
                                    // Set shouldAnimate back to true when the dialog is dismissed
                                    shouldAnimate = true
                                },
                                userPreference = userPreference
                            )
                        }
                        emptyList()
                    }
                }

                1 -> {
                    // Type 2 Recommendation
                    listOf(
                        "Type 2 Recommendation X",
                        "Type 2 Recommendation Y",
                        "Type 2 Recommendation Z"
                    )
                }

                2 -> {
                    // Type 3 Recommendation
                    listOf(
                        "Type 3 Recommendation Alpha",
                        "Type 3 Recommendation Beta",
                        "Type 3 Recommendation Gamma"
                    )
                }

                else -> emptyList()
            }

            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    content.forEach {
                        Text(text = it)
                    }
                }
            }
        }
    }
}

@Composable
fun FillFormDialog(
    onDismiss: () -> Unit,
    userPreference: UserPreference,
    viewModel: FormViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
) {
    val coroutineScope = rememberCoroutineScope()
    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }

    val sensitivities = listOf("asdadf", "sdds", "sdsadsd")
    val medHistories = listOf("ASADAD", "ADFDF", "ADSFE")

    Dialog(
        onDismissRequest = { onDismiss },
        content = {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Column {
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
                }

                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        coroutineScope.launch {
                            userPreference.saveMedicalData(
                                viewModel.sensitivity,
                                viewModel.medHistory
                            )
                        }
                        // Instead of setting confirmed to true, manually call onDismiss
                        onDismiss()
                    }) {
                        Text("Save")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = {
                        // No changes made, simply dismiss the dialog
                        onDismiss()
                    }) {
                        Text("Cancel")
                    }
                }
            }
        }
    )
}

@Composable
fun DropdownField(
    label: String,
    value: String,
    expanded: Boolean,
    items: List<String>,
    onValueChange: (String) -> Unit,
    onExpand: () -> Unit,
    onClose: () -> Unit,
) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
            .clickable { onExpand() }
    ) {
        Text(
            text = if (value.isBlank()) "Select $label" else value,
            color = if (value.isBlank()) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onClose() },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        onValueChange(item)

                        onClose()
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun DialogFormPreview() {
    val userPreference = UserPreference.getInstance(context = LocalContext.current)
    FillFormDialog(onDismiss = {},userPreference)
}