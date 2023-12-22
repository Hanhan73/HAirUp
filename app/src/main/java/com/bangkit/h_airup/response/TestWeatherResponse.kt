package com.bangkit.h_airup.response

import com.google.gson.annotations.SerializedName

data class TestWeatherResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

