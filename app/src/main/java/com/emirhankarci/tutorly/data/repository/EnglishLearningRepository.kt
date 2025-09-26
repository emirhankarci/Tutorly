package com.emirhankarci.tutorly.data.repository

import com.emirhankarci.tutorly.data.api.EnglishLearningApiService
import com.emirhankarci.tutorly.data.model.*

class EnglishLearningRepository(private val apiService: EnglishLearningApiService) {

    suspend fun getEnglishLevels(): Result<EnglishLevelsResponse> {
        return try {
            val response = apiService.getEnglishLevels()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get English levels: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun generateEnglishContent(
        prompt: String,
        maxTokens: Int = 1000,
        temperature: Float = 0.7f
    ): Result<EnglishLearningResponse> {
        return try {
            val request = EnglishLearningRequest(prompt, maxTokens, temperature)
            val response = apiService.generateEnglishContent(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("English content generation failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}