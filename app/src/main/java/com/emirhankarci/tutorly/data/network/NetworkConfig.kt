package com.emirhankarci.tutorly.data.network

import com.emirhankarci.tutorly.data.api.GeminiApiService
import com.emirhankarci.tutorly.data.api.EnglishLearningApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkConfig {
    private const val BASE_URL = "http://81.214.137.5:8000/" // Use 10.0.2.2 for Android emulator to access localhost

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val geminiApiService: GeminiApiService = retrofit.create(GeminiApiService::class.java)
    val englishLearningApiService: EnglishLearningApiService = retrofit.create(EnglishLearningApiService::class.java)
}