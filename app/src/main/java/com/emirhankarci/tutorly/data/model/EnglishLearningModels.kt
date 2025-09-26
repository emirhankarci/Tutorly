package com.emirhankarci.tutorly.data.model

data class EnglishLearningRequest(
    val prompt: String,
    val max_tokens: Int = 1000,
    val temperature: Float = 0.7f
)

data class EnglishLearningResponse(
    val generated_text: String,
    val detected_level: String,
    val system_prompt_used: String,
    val clean_prompt: String
)

data class EnglishLevelsResponse(
    val levels: Map<String, String>,
    val usage: String,
    val workflow: String
)

data class EnglishLevel(
    val code: String,
    val name: String,
    val description: String
)