package com.emirhankarci.tutorly.data.repository

import com.emirhankarci.tutorly.data.api.GeminiApiService
import com.emirhankarci.tutorly.data.model.*
import com.emirhankarci.tutorly.domain.entity.QuestionType
import com.emirhankarci.tutorly.domain.entity.QuizQuestion
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

    suspend fun generateQuiz(
        grade: Int,
        subject: String,
        chapter: String,
        count: Int,
        questionType: QuestionType,
        difficulty: String = "orta",
        language: String = "tr"
    ): Result<List<QuizQuestion>> {
        return try {
            val request = QuizRequest(
                sinif = grade,
                ders = subject,
                konu = chapter,
                soru_sayisi = count,
                soru_tipi = when (questionType) {
                    QuestionType.TEST -> "coktan_secmeli"
                    QuestionType.OPEN_ENDED -> "acik_uclu"
                },
                zorluk = difficulty,
                dil = language
            )
            val response = apiService.generateQuiz(request)
            if (response.isSuccessful && response.body() != null) {
                val envelope = response.body()!!
                if (envelope.success && envelope.data != null) {
                    val mapped = mapQuizResponseToDomain(envelope.data)
                    return Result.success(mapped)
                }
                val code = response.code()
                return Result.failure(Exception("Quiz generation failed: ${envelope.message ?: "Unknown error"} ($code)"))
            } else {
                Result.failure(Exception("Quiz generation failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun mapQuizResponseToDomain(api: QuizResponse): List<QuizQuestion> {
        val type = api.soru_tipi
        return api.sorular.mapIndexed { index, item ->
            if (type == "coktan_secmeli") {
                val soru = item["soru"] as? String ?: ""
                val a = item["a"] as? String ?: ""
                val b = item["b"] as? String ?: ""
                val c = item["c"] as? String ?: ""
                val d = item["d"] as? String ?: ""
                val cevap = (item["cevap"] as? String)?.lowercase() ?: ""
                val aciklama = item["aciklama"] as? String ?: ""
                val options = listOf(a, b, c, d)
                val correctIndex = when (cevap) {
                    "a" -> 0
                    "b" -> 1
                    "c" -> 2
                    "d" -> 3
                    else -> -1
                }
                QuizQuestion(
                    id = index + 1,
                    question = soru,
                    type = QuestionType.TEST,
                    options = options,
                    correctAnswerIndex = correctIndex,
                    explanation = aciklama
                )
            } else {
                val soru = item["soru"] as? String ?: ""
                val cevap = item["cevap"] as? String ?: ""
                val aciklama = item["aciklama"] as? String ?: ""
                QuizQuestion(
                    id = index + 1,
                    question = soru,
                    type = QuestionType.OPEN_ENDED,
                    correctAnswer = cevap,
                    explanation = aciklama
                )
            }
        }
    }
}