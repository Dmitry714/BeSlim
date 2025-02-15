package com.ctrlz.beslim.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://f1074942.xsph.ru/"

    val api: com.ctrlz.beslim.api.ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.ctrlz.beslim.api.ApiService::class.java)
    }
}