package com.bangkit.h_airup.response

import com.farhan.jetonepiece.ui.navigation.Screen
import com.google.gson.annotations.SerializedName

data class APIResponse(

	@field:SerializedName("coordinates")
	val coordinates: Coordinates? = null,

	@field:SerializedName("weather")
	val weather: Weather? = null,

	@field:SerializedName("aqi")
	val aqi: Aqi? = null
)

data class PollutantsItem(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("displayName")
	val displayName: String? = null,

	@field:SerializedName("additionalInfo")
	val additionalInfo: AdditionalInfo? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("concentration")
	val concentration: Concentration? = null
)

data class IndexesItem(

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

data class Color(

	@field:SerializedName("red")
	val red: Any? = null,

	@field:SerializedName("green")
	val green: Any? = null,

	@field:SerializedName("blue")
	val blue: Any? = null
)

data class Sys(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("sunrise")
	val sunrise: Int? = null,

	@field:SerializedName("sunset")
	val sunset: Int? = null
)

data class Clouds(

	@field:SerializedName("all")
	val all: Int? = null
)

data class AdditionalInfo(

	@field:SerializedName("effects")
	val effects: String? = null,

	@field:SerializedName("sources")
	val sources: String? = null
)

data class Concentration(

	@field:SerializedName("units")
	val units: String? = null,

	@field:SerializedName("value")
	val value: Any? = null
)

data class Coordinates(

	@field:SerializedName("lng")
	val lng: Any? = null,

	@field:SerializedName("lat")
	val lat: Any? = null
)

data class Coord(

	@field:SerializedName("lon")
	val lon: Any? = null,

	@field:SerializedName("lat")
	val lat: Any? = null
)

data class HealthRecommendations(

	@field:SerializedName("generalPopulation")
	val generalPopulation: String? = null,

	@field:SerializedName("children")
	val children: String? = null,

	@field:SerializedName("elderly")
	val elderly: String? = null,

	@field:SerializedName("athletes")
	val athletes: String? = null,

	@field:SerializedName("lungDiseasePopulation")
	val lungDiseasePopulation: String? = null,

	@field:SerializedName("heartDiseasePopulation")
	val heartDiseasePopulation: String? = null,

	@field:SerializedName("pregnantWomen")
	val pregnantWomen: String? = null
)

data class Aqi(

	@field:SerializedName("dateTime")
	val dateTime: String? = null,

	@field:SerializedName("regionCode")
	val regionCode: String? = null,

	@field:SerializedName("healthRecommendations")
	val healthRecommendations: HealthRecommendations? = null,

	@field:SerializedName("indexes")
	val indexes: List<IndexesItem?>? = null,

	@field:SerializedName("pollutants")
	val pollutants: List<PollutantsItem?>? = null
)

data class Main(

	@field:SerializedName("temp")
	val temp: Any? = null,

	@field:SerializedName("temp_min")
	val tempMin: Any? = null,

	@field:SerializedName("grnd_level")
	val grndLevel: Int? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("pressure")
	val pressure: Int? = null,

	@field:SerializedName("sea_level")
	val seaLevel: Int? = null,

	@field:SerializedName("feels_like")
	val feelsLike: Any? = null,

	@field:SerializedName("temp_max")
	val tempMax: Any? = null
)

data class Weather(

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
)

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

data class Wind(

	@field:SerializedName("deg")
	val deg: Int? = null,

	@field:SerializedName("speed")
	val speed: Any? = null,

	@field:SerializedName("gust")
	val gust: Any? = null
)
