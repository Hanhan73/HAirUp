package com.bangkit.h_airup.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ForecastResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("semarang")
	val semarang: Semarang? = null,

	@field:SerializedName("jakarta")
	val jakarta: Jakarta? = null,

	@field:SerializedName("bandung")
	val bandung: Bandung? = null
)

data class Semarang(

	@field:SerializedName("PastDataAQI")
	val pastDataAQI: List<PastDataAQIItem?>? = null,

	@field:SerializedName("currentDataAQI")
	val currentDataAQI: CurrentDataAQI? = null,

	@field:SerializedName("foreCastAQI")
	val foreCastAQI: List<ForeCastAQIItem?>? = null
)

data class PastDataAQIItem(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("color")
	val color: Color? = null,

	@field:SerializedName("displayName")
	val displayName: String? = null,

	@field:SerializedName("dominantPollutant")
	val dominantPollutant: String? = null,

	@field:SerializedName("aqi")
	val aqi: Int? = null,

	@field:SerializedName("aqiDisplay")
	val aqiDisplay: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)

data class Bandung(

	@field:SerializedName("PastDataAQI")
	val pastDataAQI: List<PastDataAQIItem?>? = null,

	@field:SerializedName("currentDataAQI")
	val currentDataAQI: CurrentDataAQI? = null,

	@field:SerializedName("foreCastAQI")
	val foreCastAQI: List<ForeCastAQIItem?>? = null
)

data class Jakarta(

	@field:SerializedName("PastDataAQI")
	val pastDataAQI: List<PastDataAQIItem?>? = null,

	@field:SerializedName("currentDataAQI")
	val currentDataAQI: CurrentDataAQI? = null,

	@field:SerializedName("foreCastAQI")
	val foreCastAQI: List<ForeCastAQIItem?>? = null
)

data class CurrentDataAQI(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("color")
	val color: Color? = null,

	@field:SerializedName("displayName")
	val displayName: String? = null,

	@field:SerializedName("dominantPollutant")
	val dominantPollutant: String? = null,

	@field:SerializedName("aqi")
	val aqi: Int? = null,

	@field:SerializedName("aqiDisplay")
	val aqiDisplay: String? = null,

	@field:SerializedName("category")
	val category: String? = null
) : Parcelable {

	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readParcelable(Color::class.java.classLoader),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString()
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(code)
//		parcel.writeParcelable(color, flags)
		parcel.writeString(displayName)
		parcel.writeString(dominantPollutant)
		parcel.writeValue(aqi)
		parcel.writeString(aqiDisplay)
		parcel.writeString(category)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<CurrentDataAQI> {
		override fun createFromParcel(parcel: Parcel): CurrentDataAQI {
			return CurrentDataAQI(parcel)
		}

		override fun newArray(size: Int): Array<CurrentDataAQI?> {
			return arrayOfNulls(size)
		}
	}
}

data class ForeCastAQIItem(

	@field:SerializedName("medianAQI")
	val medianAQI: Int? = null,

	@field:SerializedName("dominantPollutant")
	val dominantPollutant: String? = null
)
