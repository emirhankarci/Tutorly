package com.emirhankarci.tutorly.data.api

import com.emirhankarci.tutorly.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface TutorlyAIService {

    @POST("/generate")
    suspend fun generateText(@Body request: TextGenerationRequest): Response<TextGenerationResponse>

    @POST("/quiz/generate")
    suspend fun generateQuiz(@Body request: QuizRequest): Response<QuizResponse>

    @POST("/english/generate")
    suspend fun generateEnglish(@Body request: EnglishRequest): Response<EnglishResponse>

    @POST("/image/generate")
    suspend fun generateImage(@Body request: ImageRequest): Response<ImageResponse>
}