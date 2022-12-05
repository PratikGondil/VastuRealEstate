package com.vastu.networkService.client

import com.vastu.networkService.api.IApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    var BASE_URL = "https://vastu.myclanservices.co.in/"

    var httpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    fun getInstance() : IApiInterface {
        val retrofit = Retrofit.Builder()

            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(httpClient)
            .build()
        return  retrofit.create(IApiInterface::class.java)
    }
}