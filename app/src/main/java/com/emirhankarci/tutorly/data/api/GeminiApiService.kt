package com.emirhankarci.tutorly.data.api

import com.emirhankarci.tutorly.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface GeminiApiService {

    @GET("/")
    suspend fun getRoot(): Response<ApiRootResponse>

    @GET("/health")
    suspend fun getHealth(): Response<ApiHealthResponse>

    @POST("/generate")
    suspend fun generateText(@Body request: TextGenerationRequest): Response<TextGenerationResponse>

    @GET("/models")
    suspend fun getAvailableModels(): Response<AvailableModelsResponse>
}