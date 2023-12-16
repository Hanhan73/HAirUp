package com.bangkit.h_airup.ui.screen.welcome

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bangkit.h_airup.data.AqiRepository

class FormViewModel(
    private val repository: AqiRepository
) : ViewModel() {
    var name by mutableStateOf("")
    var city by mutableStateOf("")
    var province by mutableStateOf("")
    var age by mutableStateOf("")
    var sensitivity by mutableStateOf("")
    var medHistory by mutableStateOf("")
    var filteredCities by mutableStateOf<List<String>>(emptyList())


}