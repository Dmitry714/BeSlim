package com.ctrlz.beslim.api

import com.ctrlz.beslim.model.ApiResponse
import com.ctrlz.beslim.model.PostLogin
import retrofit2.Response
import retrofit2.http.*

interface LoginUser {
    @POST("includes/login.php")
    suspend fun loginUser(
        @Body postLogin: PostLogin
    ): Response<ApiResponse>
}