package com.emirhankarci.tutorly.data.model

data class TextGenerationRequest(
    val prompt: String,
    val max_tokens: Int = 1000,
    val temperature: Float = 0.7f
)