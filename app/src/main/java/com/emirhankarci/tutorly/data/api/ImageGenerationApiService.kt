package com.emirhankarci.tutorly.data.api

import com.emirhankarci.tutorly.data.model.ImageGenerationRequest
import com.emirhankarci.tutorly.data.model.ImageGenerationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageGenerationApiService {

    @POST("/generate/image")
    suspend fun generateImage(@Body request: ImageGenerationRequest): Response<ImageGenerationResponse>
}