package com.emirhankarci.tutorly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.data.network.NetworkConfig
import com.emirhankarci.tutorly.data.repository.EnglishLearningRepository
import com.emirhankarci.tutorly.data.model.EnglishLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EnglishLearningUiState(
    val isLoading: Boolean = false,
    val messages: List<ChatMessage> = emptyList(),
    val errorMessage: String = "",
    val availableLevels: List<EnglishLevel> = emptyList(),
    val selectedLevel: String = "b1",
    val isInitialized: Boolean = false,
    val detectedLevel: String = "",
    val cleanPrompt: String = ""
)

class EnglishLearningViewModel : ViewModel() {

    private val repository = EnglishLearningRepository(NetworkConfig.englishLearningApiService)

    private val _uiState = MutableStateFlow(EnglishLearningUiState())
    val uiState: StateFlow<EnglishLearningUiState> = _uiState.asStateFlow()

    init {
        loadAvailableLevels()
        initializeChat()
    }

    private fun loadAvailableLevels() {
        viewModelScope.launch {
            repository.getEnglishLevels().fold(
                onSuccess = { response ->
                    val levels = response.levels.map { (code, description) ->
                        EnglishLevel(
                            code = code.lowercase(),
                            name = code.uppercase(),
                            description = description
                        )
                    }
                    _uiState.value = _uiState.value.copy(
                        availableLevels = levels
                    )
                },
                onFailure = {
                    // Use default levels if API fails
                    val defaultLevels = listOf(
                        EnglishLevel("a1", "A1", "Başlangıç - Temel kelimeler ve basit cümleler"),
                        EnglishLevel("a2", "A2", "Temel - Günlük konular ve basit gramer"),
                        EnglishLevel("b1", "B1", "Orta - Karmaşık konular ve gelişmiş gramer"),
                        EnglishLevel("b2", "B2", "Üst-Orta - Soyut konular ve akademik dil"),
                        EnglishLevel("c1", "C1", "İleri - Profesyonel ve akademik İngilizce"),
                        EnglishLevel("c2", "C2", "Usta - Native speaker seviyesi")
                    )
                    _uiState.value = _uiState.value.copy(
                        availableLevels = defaultLevels
                    )
                }
            )
        }
    }

    private fun initializeChat() {
        if (_uiState.value.isInitialized) return

        val welcomeMessage = generateWelcomeMessage()
        _uiState.value = _uiState.value.copy(
            messages = listOf(
                ChatMessage(content = welcomeMessage, isUser = false)
            ),
            isInitialized = true
        )
    }

    private fun generateWelcomeMessage(): String {
        return "Merhaba! Ben İngilizce öğrenme için yapay zekâ asistanınım! 🌟\n\n" +
                "İngilizcenizi farklı seviyelerde (A1-C2) geliştirmenize yardımcı olabilirim. Şunları yapabilirsiniz:\n" +
                "• Konuşma pratiği yapın\n" +
                "• Dil bilgisi kurallarını öğrenin\n" +
                "• Kelime bilginizi geliştirin\n" +
                "• Yazım konusunda yardım alın\n\n" +
                "Başlamak için seviyenizi şu şekilde belirtebilirsiniz: \"ingilizce seviyesi: b1\" ya da direkt sohbete başlayın!\n\n" +
                "Bugün ne çalışmak istersiniz?"
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
            // Build the full prompt with conversation context
            val fullPrompt = buildFullPrompt(message)

            repository.generateEnglishContent(fullPrompt).fold(
                onSuccess = { response ->
                    val aiMessage = ChatMessage(content = response.generated_text, isUser = false)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        messages = _uiState.value.messages + aiMessage,
                        detectedLevel = response.detected_level,
                        cleanPrompt = response.clean_prompt
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

    private fun buildFullPrompt(newMessage: String): String {
        // Add level information to the prompt if it's not already specified
        val levelPrefix = if (!newMessage.contains("ingilizce seviyesi:", ignoreCase = true)) {
            "ingilizce seviyesi: ${_uiState.value.selectedLevel}. "
        } else ""

        // Build conversation context
        val recentMessages = _uiState.value.messages.takeLast(6) // Keep last 6 messages for context
        val context = if (recentMessages.isNotEmpty()) {
            val conversationHistory = recentMessages.joinToString("\n") { msg ->
                if (msg.isUser) "Student: ${msg.content}" else "Teacher: ${msg.content}"
            }
            "\n\nConversation history:\n$conversationHistory\n\nNew message: "
        } else {
            ""
        }

        return "$levelPrefix$context$newMessage"
    }

    fun setSelectedLevel(level: String) {
        _uiState.value = _uiState.value.copy(selectedLevel = level.lowercase())
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = "")
    }

    fun clearChat() {
        _uiState.value = _uiState.value.copy(
            messages = emptyList(),
            isInitialized = false
        )
        initializeChat()
    }
}