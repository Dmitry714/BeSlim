package com.ctrlz.beslim.api

import com.ctrlz.beslim.model.ApiResponse
import com.ctrlz.beslim.model.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("index.php")
    suspend fun addUser(
        @Body user: User
    ): Response<ApiResponse>
}