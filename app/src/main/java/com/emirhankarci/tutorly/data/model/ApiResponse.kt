package com.emirhankarci.tutorly.data.model

data class ApiHealthResponse(
    val status: String
)

data class ApiRootResponse(
    val message: String,
    val status: String,
    val docs_url: String
)

data class AvailableModelsResponse(
    val available_models: List<String>?,
    val current_model: String?
)