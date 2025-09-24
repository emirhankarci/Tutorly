package com.emirhankarci.tutorly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.data.network.NetworkConfig
import com.emirhankarci.tutorly.data.repository.GeminiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChatMessage(
    val content: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

data class GeminiUiState(
    val isLoading: Boolean = false,
    val messages: List<ChatMessage> = emptyList(),
    val errorMessage: String = "",
    val isConnected: Boolean = false,
    val availableModels: List<String> = emptyList(),
    val currentModel: String = "",
    val isInitialized: Boolean = false
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

    fun initializeChat(grade: Int, subject: String, chapter: String) {
        if (_uiState.value.isInitialized) return

        val welcomeMessage = generateWelcomeMessage(grade, subject, chapter)
        _uiState.value = _uiState.value.copy(
            messages = listOf(
                ChatMessage(content = welcomeMessage, isUser = false)
            ),
            isInitialized = true
        )
    }

    private fun generateWelcomeMessage(grade: Int, subject: String, chapter: String): String {
        return "Merhaba! Ben ${grade}. sınıf $subject dersi '$chapter' konusu hakkında sana yardımcı olacak AI asistanınım. \n\nBu konu hakkında sorularını sorabilir, kavramları açıklamamı isteyebilir veya örnekler vermemi sağlayabilirsin. \n\nNe öğrenmek istersin?"
    }

    fun sendMessage(message: String) {
        if (message.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Lütfen bir mesaj girin")
            return
        }

        val userMessage = ChatMessage(content = message, isUser = true)
        _uiState.value = _uiState.value.copy(
            messages = _uiState.value.messages + userMessage,
            isLoading = true,
            errorMessage = ""
        )

        viewModelScope.launch {
            val conversationContext = buildConversationContext()
            repository.generateText(conversationContext, 1000, 0.7f).fold(
                onSuccess = { response ->
                    val aiMessage = ChatMessage(content = response.generated_text, isUser = false)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        messages = _uiState.value.messages + aiMessage
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Mesaj gönderilemedi: ${error.message}"
                    )
                }
            )
        }
    }

    private fun buildConversationContext(): String {
        val messages = _uiState.value.messages
        return messages.joinToString("\n\n") { message ->
            if (message.isUser) {
                "Öğrenci: ${message.content}"
            } else {
                "AI Öğretmen: ${message.content}"
            }
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

    fun clearChat() {
        _uiState.value = _uiState.value.copy(
            messages = emptyList(),
            isInitialized = false
        )
    }
}