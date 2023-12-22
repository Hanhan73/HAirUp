package com.bangkit.h_airup.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class WeathersResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("semarang")
	val semarang: Semarang2? = null,

	@field:SerializedName("jakarta")
	val jakarta: Jakarta1? = null,

	@field:SerializedName("bandung")
	val bandung: Bandung3? = null
)

data class Coord(

	@field:SerializedName("lon")
	val lon: Any? = null,

	@field:SerializedName("lat")
	val lat: Any? = null
)

data class PastDataWeatherItem(

	@field:SerializedName("pres")
	val pres: Int? = null,

	@field:SerializedName("min_temp_ts")
	val minTempTs: Int? = null,

	@field:SerializedName("clouds")
	val clouds: Int? = null,

	@field:SerializedName("max_wind_dir")
	val maxWindDir: Int? = null,

	@field:SerializedName("max_wind_spd_ts")
	val maxWindSpdTs: Int? = null,

	@field:SerializedName("wind_spd")
	val windSpd: Any? = null,

	@field:SerializedName("t_solar_rad")
	val tSolarRad: Int? = null,

	@field:SerializedName("max_dni")
	val maxDni: Int? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("precip_gpm")
	val precipGpm: Any? = null,

	@field:SerializedName("t_ghi")
	val tGhi: Int? = null,

	@field:SerializedName("max_uv")
	val maxUv: Any? = null,

	@field:SerializedName("precip")
	val precip: Any? = null,

	@field:SerializedName("min_temp")
	val minTemp: Any? = null,

	@field:SerializedName("t_dhi")
	val tDhi: Int? = null,

	@field:SerializedName("max_temp_ts")
	val maxTempTs: Int? = null,

	@field:SerializedName("max_ghi")
	val maxGhi: Int? = null,

	@field:SerializedName("t_dni")
	val tDni: Int? = null,

	@field:SerializedName("max_temp")
	val maxTemp: Int? = null,

	@field:SerializedName("snow_depth")
	val snowDepth: Any? = null,

	@field:SerializedName("dni")
	val dni: Int? = null,

	@field:SerializedName("max_dhi")
	val maxDhi: Int? = null,

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("dhi")
	val dhi: Int? = null,

	@field:SerializedName("revision_status")
	val revisionStatus: String? = null,

	@field:SerializedName("ghi")
	val ghi: Int? = null,

	@field:SerializedName("dewpt")
	val dewpt: Any? = null,

	@field:SerializedName("wind_dir")
	val windDir: Int? = null,

	@field:SerializedName("solar_rad")
	val solarRad: Int? = null,

	@field:SerializedName("wind_gust_spd")
	val windGustSpd: Any? = null,

	@field:SerializedName("max_wind_spd")
	val maxWindSpd: Any? = null,

	@field:SerializedName("rh")
	val rh: Any? = null,

	@field:SerializedName("slp")
	val slp: Int? = null,

	@field:SerializedName("snow")
	val snow: Int? = null,

	@field:SerializedName("ts")
	val ts: Int? = null
)

data class CurrentDataWeather(

	@field:SerializedName("visibility")
	val visibility: Int? = null,

	@field:SerializedName("timezone")
	val timezone: Int? = null,

	@field:SerializedName("main")
	val main: Main? = null,

	@field:SerializedName("clouds")
	val clouds: Clouds? = null,

	@field:SerializedName("sys")
	val sys: Sys? = null,

	@field:SerializedName("dt")
	val dt: Int? = null,

	@field:SerializedName("coord")
	val coord: Coord? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("cod")
	val cod: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("base")
	val base: String? = null,

	@field:SerializedName("wind")
	val wind: Wind? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readValue(Int::class.java.classLoader) as? Int,
		TODO("main"),
		TODO("clouds"),
		TODO("sys"),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		TODO("coord"),
		TODO("weather"),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		TODO("wind")
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeValue(visibility)
		parcel.writeValue(timezone)
		parcel.writeValue(dt)
		parcel.writeString(name)
		parcel.writeValue(cod)
		parcel.writeValue(id)
		parcel.writeString(base)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<CurrentDataWeather> {
		override fun createFromParcel(parcel: Parcel): CurrentDataWeather {
			return CurrentDataWeather(parcel)
		}

		override fun newArray(size: Int): Array<CurrentDataWeather?> {
			return arrayOfNulls(size)
		}
	}
}

data class WeatherItem(

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("main")
	val main: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Jakarta1(

	@field:SerializedName("foreCastWeather")
	val foreCastWeather: List<ForeCastWeatherItem?>? = null,

	@field:SerializedName("PastDataWeather")
	val pastDataWeather: List<PastDataWeatherItem?>? = null,

	@field:SerializedName("currentDataWeather")
	val currentDataWeather: CurrentDataWeather? = null
)

data class Clouds(

	@field:SerializedName("all")
	val all: Int? = null
)

data class Semarang2(

	@field:SerializedName("foreCastWeather")
	val foreCastWeather: List<ForeCastWeatherItem?>? = null,

	@field:SerializedName("PastDataWeather")
	val pastDataWeather: List<PastDataWeatherItem?>? = null,

	@field:SerializedName("currentDataWeather")
	val currentDataWeather: CurrentDataWeather? = null
)

data class Main(

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("temp_min")
	val tempMin: Any? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("pressure")
	val pressure: Int? = null,

	@field:SerializedName("feels_like")
	val feelsLike: Double? = null,

	@field:SerializedName("temp_max")
	val tempMax: Any? = null
)


data class Bandung3(

	@field:SerializedName("foreCastWeather")
	val foreCastWeather: List<ForeCastWeatherItem?>? = null,

	@field:SerializedName("PastDataWeather")
	val pastDataWeather: List<PastDataWeatherItem?>? = null,

	@field:SerializedName("currentDataWeather")
	val currentDataWeather: CurrentDataWeather? = null
)

data class ForeCastWeatherItem(

	@field:SerializedName("RR")
	val rR: Double? = null,

	@field:SerializedName("Tx")
	val tx: Double? = null,

	@field:SerializedName("RH_avg")
	val rHAvg: Double? = null,

	@field:SerializedName("Tn")
	val tn: Double? = null
)

data class Sys(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("sunrise")
	val sunrise: Int? = null,

	@field:SerializedName("sunset")
	val sunset: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: Int? = null
)
