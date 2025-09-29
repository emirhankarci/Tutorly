package com.emirhankarci.tutorly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.data.model.LessonPlanRequest
import com.emirhankarci.tutorly.data.network.ApiClient
import com.emirhankarci.tutorly.domain.entity.ChatMessage
import com.emirhankarci.tutorly.domain.entity.ScheduleItem
import com.emirhankarci.tutorly.domain.util.LessonPlanParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LessonPlanChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val lastGeneratedPlan: String? = null,
    val parsedLessons: List<ScheduleItem> = emptyList()
)

@HiltViewModel
class LessonPlanChatViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LessonPlanChatUiState())
    val uiState: StateFlow<LessonPlanChatUiState> = _uiState.asStateFlow()

    private val tutorlyAIService = ApiClient.tutorlyAIService

    init {
        // Add welcome message
        addMessage(
            ChatMessage(
                content = "Merhaba! Size hangi konular için ders planı hazırlayabilirim? Lütfen konuları detaylı bir şekilde belirtin.",
                isUser = false
            )
        )
    }

    fun sendMessage(message: String) {
        // Add user message
        addMessage(ChatMessage(content = message, isUser = true))

        // Start loading
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        // Generate lesson plan
        generateLessonPlan(message)
    }

    private fun addMessage(message: ChatMessage) {
        _uiState.value = _uiState.value.copy(
            messages = _uiState.value.messages + message
        )
    }

    private fun generateLessonPlan(prompt: String) {
        viewModelScope.launch {
            try {
                val request = LessonPlanRequest(prompt = prompt)
                val response = tutorlyAIService.generateLessonPlan(request)

                if (response.isSuccessful) {
                    val lessonPlanResponse = response.body()
                    if (lessonPlanResponse != null && lessonPlanResponse.success) {
                        // Parse the lesson plan into schedule items
                        val parsedLessons = LessonPlanParser.parseAILessonPlan(lessonPlanResponse.lesson_plan)

                        // Add AI response message
                        addMessage(
                            ChatMessage(
                                content = lessonPlanResponse.lesson_plan,
                                isUser = false
                            )
                        )

                        // Set the last generated plan and parsed lessons for callback
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            lastGeneratedPlan = lessonPlanResponse.lesson_plan,
                            parsedLessons = parsedLessons
                        )
                    } else {
                        val errorMessage = lessonPlanResponse?.error_message ?: "Ders planı oluşturulurken bir hata oluştu."
                        handleError(errorMessage)
                    }
                } else {
                    handleError("API isteği başarısız: ${response.code()}")
                }
            } catch (e: Exception) {
                handleError("Ağ hatası: ${e.message}")
            }
        }
    }

    private fun handleError(errorMessage: String) {
        addMessage(
            ChatMessage(
                content = "Üzgünüm, bir hata oluştu: $errorMessage\nLütfen tekrar deneyin.",
                isUser = false
            )
        )

        _uiState.value = _uiState.value.copy(
            isLoading = false,
            error = errorMessage
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}