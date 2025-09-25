package com.emirhankarci.tutorly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.data.network.NetworkConfig
import com.emirhankarci.tutorly.data.repository.GeminiRepository
import com.emirhankarci.tutorly.domain.entity.QuestionType
import com.emirhankarci.tutorly.domain.entity.QuizQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class QuizUiState(
    val isLoading: Boolean = false,
    val questions: List<QuizQuestion> = emptyList(),
    val errorMessage: String = ""
)

class QuizViewModel : ViewModel() {

    private val repository = GeminiRepository(NetworkConfig.geminiApiService)

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun loadQuiz(
        grade: Int,
        subject: String,
        chapter: String,
        questionCount: Int,
        questionType: String,
        difficulty: String = "orta",
        language: String = "tr"
    ) {
        // Prevent duplicate loads if already loaded with same content
        if (_uiState.value.isLoading) return
        if (_uiState.value.questions.isNotEmpty()) return

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = "")

        val type = if (questionType == "Test") QuestionType.TEST else QuestionType.OPEN_ENDED

        viewModelScope.launch {
            repository.generateQuiz(
                grade = grade,
                subject = subject,
                chapter = chapter,
                count = questionCount,
                questionType = type,
                difficulty = difficulty,
                language = language
            ).fold(
                onSuccess = { questions ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        questions = questions,
                        errorMessage = ""
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Quiz yüklenemedi"
                    )
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = "")
    }
}


