package com.bangkit.h_airup.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("user")
	val user: User? = null
)

data class User(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("riwayatPenyakit")
	val riwayatPenyakit: Any? = null,

	@field:SerializedName("umur")
	val umur: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("status")
	val status: Any? = null
)
