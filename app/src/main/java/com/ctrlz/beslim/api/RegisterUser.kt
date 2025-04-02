package com.ctrlz.beslim.api

import com.ctrlz.beslim.model.ApiResponse
import com.ctrlz.beslim.model.PostRegister
import retrofit2.Response
import retrofit2.http.*

interface RegisterUser {
    @POST("includes/register.php")
    suspend fun registerUser(
        @Body postRegister: PostRegister
    ): Response<ApiResponse>
}