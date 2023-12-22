package com.bangkit.h_airup.response

import com.google.gson.annotations.SerializedName

data class RecommendResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("response")
	val response: Response? = null
)

data class Response(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("pesan")
	val pesan: String? = null,

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
