package com.bangkit.h_airup.retrofit

import com.bangkit.h_airup.model.UserRequestBody
import com.bangkit.h_airup.response.APIResponse
import com.bangkit.h_airup.response.SSEResponse
import com.bangkit.h_airup.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface
ApiService {

    @GET("api/{userId}")
    @Headers("Content-Type: application/json")
    fun getApi(
        @Path("userId") userid: String
    ): Call<APIResponse>

    @POST("user")
    fun postUser(@Body requestBody: UserRequestBody) : Call<UserResponse>

    @PUT("user/{userId}")
    fun putUser(
        @Body requestBody: UserRequestBody,
        @Path("userId") userid: String) : Call<UserResponse>
}