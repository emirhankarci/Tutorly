package com.emirhankarci.tutorly.data.repository

import com.emirhankarci.tutorly.data.model.*
import com.emirhankarci.tutorly.data.network.ApiClient

class TutorlyAIRepository {
    private val api = ApiClient.tutorlyAIService

    suspend fun generateText(prompt: String): Result<TextGenerationResponse> {
        return try {
            val request = TextGenerationRequest(prompt = prompt)
            val response = api.generateText(request)

            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun generateQuiz(
        sinif: Int,
        ders: String,
        konu: String,
        soruSayisi: Int,
        soruTipi: String
    ): Result<QuizResponse> {
        return try {
            val request = QuizRequest(
                sinif = sinif,
                ders = ders,
                konu = konu,
                soru_sayisi = soruSayisi,
                soru_tipi = soruTipi
            )
            val response = api.generateQuiz(request)

            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun generateEnglishContent(prompt: String): Result<EnglishResponse> {
        return try {
            val request = EnglishRequest(prompt = prompt)
            val response = api.generateEnglish(request)

            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun generateImage(prompt: String): Result<ImageResponse> {
        return try {
            val request = ImageRequest(prompt = prompt)
            val response = api.generateImage(request)

            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}