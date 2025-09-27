package com.emirhankarci.tutorly.data.model

data class EnglishRequest(
    val prompt: String,
    val max_tokens: Int = 1000,
    val temperature: Float = 0.7f
)

data class EnglishResponse(
    val generated_text: String,
    val detected_level: String,
    val system_prompt_used: String,
    val clean_prompt: String
)

data class ImageRequest(
    val prompt: String,
    val workflow_id: String = "workflows/halillllibrahim58/teach-img-model",
    val max_tokens: Int = 2500,
    val temperature: Float = 0.7f
)

data class ImageResponse(
    val success: Boolean,
    val image_url: String?,
    val workflow_id: String,
    val prompt: String,
    val error_message: String?,
    val generated_at: String,
    val all_images: List<String>? = null,
    val total_images: Int? = null
)