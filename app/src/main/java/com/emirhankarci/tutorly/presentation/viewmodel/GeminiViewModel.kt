package com.emirhankarci.tutorly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.data.network.NetworkConfig
import com.emirhankarci.tutorly.data.repository.GeminiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GeminiUiState(
    val isLoading: Boolean = false,
    val generatedText: String = "",
    val errorMessage: String = "",
    val isConnected: Boolean = false,
    val availableModels: List<String> = emptyList(),
    val currentModel: String = ""
)

class GeminiViewModel : ViewModel() {

    private val repository = GeminiRepository(NetworkConfig.geminiApiService)

    private val _uiState = MutableStateFlow(GeminiUiState())
    val uiState: StateFlow<GeminiUiState> = _uiState.asStateFlow()

    init {
        checkConnection()
    }

    fun checkConnection() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = "")

            repository.checkHealth().fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        isConnected = true,
                        isLoading = false
                    )
                    loadAvailableModels()
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isConnected = false,
                        isLoading = false,
                        errorMessage = "Connection failed: ${error.message}"
                    )
                }
            )
        }
    }

    fun generateText(
        prompt: String,
        maxTokens: Int = 1000,
        temperature: Float = 0.7f
    ) {
        if (prompt.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Please enter a prompt")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = "",
                generatedText = ""
            )

            repository.generateText(prompt, maxTokens, temperature).fold(
                onSuccess = { response ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        generatedText = response.generated_text
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Generation failed: ${error.message}"
                    )
                }
            )
        }
    }

    private fun loadAvailableModels() {
        viewModelScope.launch {
            repository.getAvailableModels().fold(
                onSuccess = { response ->
                    _uiState.value = _uiState.value.copy(
                        availableModels = response.available_models ?: emptyList(),
                        currentModel = response.current_model ?: ""
                    )
                },
                onFailure = {
                    // Don't show error for models, it's not critical
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = "")
    }

    fun clearGeneratedText() {
        _uiState.value = _uiState.value.copy(generatedText = "")
    }
}