package com.emirhankarci.tutorly.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.domain.entity.ScheduleItem
import com.emirhankarci.tutorly.domain.repository.AuthRepository
import com.emirhankarci.tutorly.domain.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ScheduleUiState(
    val lessons: List<ScheduleItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    init {
        loadUserLessons()
    }

    private fun loadUserLessons() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            val currentUser = authRepository.getCurrentUser()
            if (currentUser != null) {
                val result = scheduleRepository.getUserLessons(currentUser.uid)
                if (result.isSuccess) {
                    val lessons = result.getOrDefault(emptyList())
                    _uiState.value = _uiState.value.copy(
                        lessons = lessons,
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message ?: "Failed to load lessons"
                    )
                }
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "User not authenticated"
                )
            }
        }
    }

    fun addLesson(lesson: ScheduleItem) {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            if (currentUser != null) {
                val result = scheduleRepository.saveLesson(currentUser.uid, lesson)
                if (result.isSuccess) {
                    // Add to local state immediately for better UX
                    val currentLessons = _uiState.value.lessons.toMutableList()
                    currentLessons.add(lesson)
                    _uiState.value = _uiState.value.copy(lessons = currentLessons)
                } else {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = result.exceptionOrNull()?.message ?: "Failed to save lesson"
                    )
                }
            }
        }
    }

    fun removeLesson(lesson: ScheduleItem) {
        // For now, just remove from local state
        // To implement deletion, we'd need lesson IDs from Firebase
        val currentLessons = _uiState.value.lessons.toMutableList()
        currentLessons.remove(lesson)
        _uiState.value = _uiState.value.copy(lessons = currentLessons)
    }

    fun updateLesson(oldLesson: ScheduleItem, newLesson: ScheduleItem) {
        // For now, just update local state
        // To implement updates, we'd need lesson IDs from Firebase
        val currentLessons = _uiState.value.lessons.toMutableList()
        val index = currentLessons.indexOf(oldLesson)
        if (index != -1) {
            currentLessons[index] = newLesson
            _uiState.value = _uiState.value.copy(lessons = currentLessons)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun refreshLessons() {
        loadUserLessons()
    }
}