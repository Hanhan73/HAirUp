package com.bangkit.h_airup.response

import com.google.gson.annotations.SerializedName

data class SSEResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)
