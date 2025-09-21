package com.emirhankarci.tutorly.data.repository

import com.emirhankarci.tutorly.data.api.GeminiApiService
import com.emirhankarci.tutorly.data.model.*
import retrofit2.Response

class GeminiRepository(private val apiService: GeminiApiService) {

    suspend fun checkHealth(): Result<ApiHealthResponse> {
        return try {
            val response = apiService.getHealth()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Health check failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRootInfo(): Result<ApiRootResponse> {
        return try {
            val response = apiService.getRoot()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Root info failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun generateText(
        prompt: String,
        maxTokens: Int = 1000,
        temperature: Float = 0.7f
    ): Result<TextGenerationResponse> {
        return try {
            val request = TextGenerationRequest(prompt, maxTokens, temperature)
            val response = apiService.generateText(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Text generation failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAvailableModels(): Result<AvailableModelsResponse> {
        return try {
            val response = apiService.getAvailableModels()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Get models failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}