package com.emirhankarci.tutorly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.data.network.NetworkConfig
import com.emirhankarci.tutorly.data.repository.ImageGenerationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StudyWithImageUiState(
    val isLoading: Boolean = false,
    val generatedImageUrl: String? = null,
    val allImages: List<String> = emptyList(),
    val totalImages: Int = 0,
    val prompt: String = "",
    val errorMessage: String? = null,
    val workflowId: String = "",
    val generatedAt: String = ""
)

class StudyWithImageViewModel : ViewModel() {

    private val repository = ImageGenerationRepository(NetworkConfig.imageGenerationApiService)

    private val _uiState = MutableStateFlow(StudyWithImageUiState())
    val uiState: StateFlow<StudyWithImageUiState> = _uiState.asStateFlow()

    fun generateImage(prompt: String) {
        if (prompt.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                prompt = prompt
            )

            repository.generateImage(prompt).fold(
                onSuccess = { response ->
                    if (response.success && response.image_url != null) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            generatedImageUrl = response.image_url,
                            allImages = response.all_images ?: listOf(response.image_url),
                            totalImages = response.total_images ?: 1,
                            workflowId = response.workflow_id,
                            generatedAt = response.generated_at,
                            errorMessage = null
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = response.error_message ?: "Failed to generate image"
                        )
                    }
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Network error occurred"
                    )
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}