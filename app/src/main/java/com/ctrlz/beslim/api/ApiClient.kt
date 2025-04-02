package com.ctrlz.beslim.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://f1077573.xsph.ru/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

    val registrationApi: RegisterUser by lazy { createService(RegisterUser::class.java) }
    val authenticationApi: LoginUser by lazy { createService(LoginUser::class.java) }

}