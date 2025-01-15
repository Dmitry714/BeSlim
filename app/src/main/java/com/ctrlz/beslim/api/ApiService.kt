package com.ctrlz.beslim.api

import com.ctrlz.beslim.model.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("path")
    suspend fun addUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ApiResponse>
}