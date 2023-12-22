package com.bangkit.h_airup.retrofit

import com.bangkit.h_airup.model.RecommendRequestBody
import com.bangkit.h_airup.model.UserRequestBody
import com.bangkit.h_airup.response.APIResponse
import com.bangkit.h_airup.response.ForecastResponse
import com.bangkit.h_airup.response.RecommendResponse
import com.bangkit.h_airup.response.TestResponse
import com.bangkit.h_airup.response.TestWeatherResponse
import com.bangkit.h_airup.response.UserResponse
import com.bangkit.h_airup.response.WeathersResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface
ApiService {

    @GET("api/user/{userId}")
    @Headers("Content-Type: application/json")
    fun getApi(
        @Path("userId") userid: String
    ): Call<APIResponse>

    @GET("api/forecastAQI")
    fun getForecast(): Call<ForecastResponse>

    @GET("test")
    fun getTest(): Call<TestResponse>

    @GET("api/forecastWeather")
    fun getForecastWeather(): Call<WeathersResponse>

    @GET("testWeather")
    fun getTestWeather(): Call<TestWeatherResponse>

    @POST("user")
    fun postUser(@Body requestBody: UserRequestBody) : Call<UserResponse>

    @Multipart
    @PUT("user/{userId}")
    fun putUser(
        @Part files: MultipartBody.Part,
        @Part("body") requestBody: UserRequestBody,
        @Path("userId") userid: String
    ): Call<UserResponse>
    @POST("api/user/{userId}")
    fun createRecommendation(
        @Body requestBody: RecommendRequestBody,
        @Path("userId") userid: String) : Call<RecommendResponse>

    @GET("api/user/{userId}")
    fun getRecommendation(
        @Path("userId") userid: String) : Call<RecommendResponse>
}