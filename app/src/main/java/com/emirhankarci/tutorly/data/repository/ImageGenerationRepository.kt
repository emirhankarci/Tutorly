package com.emirhankarci.tutorly.data.repository

import com.emirhankarci.tutorly.data.api.ImageGenerationApiService
import com.emirhankarci.tutorly.data.model.ImageGenerationRequest
import com.emirhankarci.tutorly.data.model.ImageGenerationResponse

class ImageGenerationRepository(private val apiService: ImageGenerationApiService) {

    suspend fun generateImage(prompt: String): Result<ImageGenerationResponse> {
        return try {
            val request = ImageGenerationRequest(
                prompt = prompt,
                workflow_id = "workflows/halillllibrahim58/teach-img-model",
                max_tokens = 2500,
                temperature = 0.7f
            )

            val response = apiService.generateImage(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Image generation failed: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}