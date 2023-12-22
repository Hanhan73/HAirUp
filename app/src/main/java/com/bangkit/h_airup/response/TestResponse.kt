package com.bangkit.h_airup.response

import com.google.gson.annotations.SerializedName

data class TestResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(



	@field:SerializedName("aqi")
	val aqi: Aqi? = null,

	@field:SerializedName("concentration")
	val concentration: Concentration? = null
)

data class Predictions1(

	@field:SerializedName("next_1")
	val next1: Next1? = null,

	@field:SerializedName("next_2")
	val next2: Next2? = null,

	@field:SerializedName("next_3")
	val next3: Next3? = null
)

data class Concentration1(

	@field:SerializedName("11")
	val jsonMember11: Any? = null,

	@field:SerializedName("22")
	val jsonMember22: Any? = null,

	@field:SerializedName("12")
	val jsonMember12: Any? = null,

	@field:SerializedName("23")
	val jsonMember23: Any? = null,

	@field:SerializedName("13")
	val jsonMember13: Any? = null,

	@field:SerializedName("24")
	val jsonMember24: Int? = null,

	@field:SerializedName("14")
	val jsonMember14: Any? = null,

	@field:SerializedName("15")
	val jsonMember15: Any? = null,

	@field:SerializedName("16")
	val jsonMember16: Any? = null,

	@field:SerializedName("17")
	val jsonMember17: Any? = null,

	@field:SerializedName("18")
	val jsonMember18: Any? = null,

	@field:SerializedName("19")
	val jsonMember19: Any? = null,

	@field:SerializedName("1")
	val jsonMember1: Any? = null,

	@field:SerializedName("2")
	val jsonMember2: Any? = null,

	@field:SerializedName("3")
	val jsonMember3: Any? = null,

	@field:SerializedName("4")
	val jsonMember4: Any? = null,

	@field:SerializedName("5")
	val jsonMember5: Any? = null,

	@field:SerializedName("6")
	val jsonMember6: Any? = null,

	@field:SerializedName("7")
	val jsonMember7: Any? = null,

	@field:SerializedName("8")
	val jsonMember8: Any? = null,

	@field:SerializedName("9")
	val jsonMember9: Any? = null,

	@field:SerializedName("20")
	val jsonMember20: Any? = null,

	@field:SerializedName("10")
	val jsonMember10: Any? = null,

	@field:SerializedName("21")
	val jsonMember21: Any? = null
)

data class Next1(

	@field:SerializedName("medianAQI")
	val medianAQI: Int? = null,

	@field:SerializedName("dominantPollutant")
	val dominantPollutant: String? = null,

	@field:SerializedName("perHours")
	val perHours: List<PerHoursItem?>? = null
)

data class Next2(

	@field:SerializedName("medianAQI")
	val medianAQI: Int? = null,

	@field:SerializedName("dominantPollutant")
	val dominantPollutant: String? = null,

	@field:SerializedName("perHours")
	val perHours: List<PerHoursItem?>? = null
)

data class Next3(

	@field:SerializedName("medianAQI")
	val medianAQI: Int? = null,

	@field:SerializedName("dominantPollutant")
	val dominantPollutant: String? = null,

	@field:SerializedName("perHours")
	val perHours: List<PerHoursItem?>? = null
)

data class PerHoursItem(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("pollutant")
	val pollutant: String? = null
)

data class Aqi1(

	@field:SerializedName("11")
	val jsonMember11: Int? = null,

	@field:SerializedName("22")
	val jsonMember22: Int? = null,

	@field:SerializedName("12")
	val jsonMember12: Int? = null,

	@field:SerializedName("23")
	val jsonMember23: Int? = null,

	@field:SerializedName("13")
	val jsonMember13: Int? = null,

	@field:SerializedName("24")
	val jsonMember24: Int? = null,

	@field:SerializedName("14")
	val jsonMember14: Int? = null,

	@field:SerializedName("15")
	val jsonMember15: Int? = null,

	@field:SerializedName("16")
	val jsonMember16: Int? = null,

	@field:SerializedName("17")
	val jsonMember17: Int? = null,

	@field:SerializedName("18")
	val jsonMember18: Int? = null,

	@field:SerializedName("19")
	val jsonMember19: Int? = null,

	@field:SerializedName("1")
	val jsonMember1: Int? = null,

	@field:SerializedName("2")
	val jsonMember2: Int? = null,

	@field:SerializedName("3")
	val jsonMember3: Int? = null,

	@field:SerializedName("4")
	val jsonMember4: Int? = null,

	@field:SerializedName("5")
	val jsonMember5: Int? = null,

	@field:SerializedName("6")
	val jsonMember6: Int? = null,

	@field:SerializedName("7")
	val jsonMember7: Int? = null,

	@field:SerializedName("8")
	val jsonMember8: Int? = null,

	@field:SerializedName("9")
	val jsonMember9: Int? = null,

	@field:SerializedName("20")
	val jsonMember20: Int? = null,

	@field:SerializedName("10")
	val jsonMember10: Int? = null,

	@field:SerializedName("21")
	val jsonMember21: Int? = null
)
