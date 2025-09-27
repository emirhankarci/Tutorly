package com.emirhankarci.tutorly.data.model

data class ImageGenerationRequest(
    val prompt: String,
    val workflow_id: String = "workflows/halillllibrahim58/teach-img-model",
    val max_tokens: Int = 2500,
    val temperature: Float = 0.7f
)

data class ImageGenerationResponse(
    val success: Boolean,
    val image_url: String?,
    val workflow_id: String,
    val prompt: String,
    val error_message: String?,
    val generated_at: String,
    val all_images: List<String>? = null,
    val total_images: Int? = null
)