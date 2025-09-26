package com.emirhankarci.tutorly.data.api

import com.emirhankarci.tutorly.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface EnglishLearningApiService {

    @GET("/english/levels")
    suspend fun getEnglishLevels(): Response<EnglishLevelsResponse>

    @POST("/english/generate")
    suspend fun generateEnglishContent(@Body request: EnglishLearningRequest): Response<EnglishLearningResponse>

    // Note: Streaming endpoints would typically use @Streaming annotation
    // but for simplicity, we'll start with non-streaming implementation
}