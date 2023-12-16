package com.bangkit.h_airup.retrofit

import com.bangkit.h_airup.model.UserRequestBody
import com.bangkit.h_airup.response.APIResponse
import com.bangkit.h_airup.response.SSEResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface
ApiService {
    @GET("notifikasi/events")
    @Headers("Content-Type: text/event-stream")
    suspend fun getSSE(): Call<String>

    @GET("api")
    @Headers("Content-Type: application/json")
    fun getApi(): Call<APIResponse>

    @POST("user")
    suspend fun postUser(@Body requestBody: UserRequestBody)
}