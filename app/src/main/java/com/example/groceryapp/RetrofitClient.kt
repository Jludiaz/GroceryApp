package com.example.groceryapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
The purpose of this file is to establish a successful connection
between KrogerAPI and android app
I will use RetroFit due to its ability to parse JSON files vs the traditional
way we managed JSON files with JSONObject in news manager
I decided to use RetroFit because its easier and automatically converts JSON responses
into Kotlin data classes instead of using for loops (manually)
https://square.github.io/retrofit/
*/

object RetrofitClient {
    private const val BASE_URL = "https://api.kroger.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Equivalent to Request Builder
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
