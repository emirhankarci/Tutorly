package com.emirhankarci.tutorly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.data.model.GradeSummary
import com.emirhankarci.tutorly.data.repository.SummaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SummaryUiState(
    val isLoading: Boolean = false,
    val summary: GradeSummary? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val summaryRepository: SummaryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SummaryUiState())
    val uiState: StateFlow<SummaryUiState> = _uiState.asStateFlow()

    fun loadSummary(grade: Int, subject: String, chapter: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            summaryRepository.getSummary(grade, subject, chapter)
                .onSuccess { result ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        summary = result,
                        errorMessage = if (result == null) "Özet bulunamadı" else null
                    )
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Bilinmeyen hata"
                    )
                }
        }
    }
}


