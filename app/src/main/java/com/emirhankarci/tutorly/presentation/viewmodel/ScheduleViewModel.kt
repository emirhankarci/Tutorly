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
    val errorMessage: String? = null,
    val generatedLessonPlan: String? = null
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
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            if (currentUser != null) {
                // Remove from local state immediately for better UX
                val currentLessons = _uiState.value.lessons.toMutableList()
                currentLessons.remove(lesson)
                _uiState.value = _uiState.value.copy(lessons = currentLessons)

                // Delete from Firebase using lesson ID
                val result = scheduleRepository.deleteLesson(currentUser.uid, lesson.id)
                if (!result.isSuccess) {
                    // If deletion fails, re-add the lesson to local state
                    val updatedLessons = _uiState.value.lessons.toMutableList()
                    updatedLessons.add(lesson)
                    _uiState.value = _uiState.value.copy(
                        lessons = updatedLessons,
                        errorMessage = result.exceptionOrNull()?.message ?: "Failed to delete lesson"
                    )
                }
            }
        }
    }

    fun removeLessonById(lessonId: String) {
        val lesson = _uiState.value.lessons.find { it.id == lessonId }
        lesson?.let { removeLesson(it) }
    }

    fun updateLesson(oldLesson: ScheduleItem, newLesson: ScheduleItem) {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            if (currentUser != null) {
                // Update local state immediately for better UX
                val currentLessons = _uiState.value.lessons.toMutableList()
                val index = currentLessons.indexOf(oldLesson)
                if (index != -1) {
                    currentLessons[index] = newLesson
                    _uiState.value = _uiState.value.copy(lessons = currentLessons)
                }

                // Update in Firebase using lesson ID
                val result = scheduleRepository.updateLesson(currentUser.uid, oldLesson.id, newLesson)
                if (!result.isSuccess) {
                    // If update fails, revert the local state
                    val revertedLessons = _uiState.value.lessons.toMutableList()
                    val revertIndex = revertedLessons.indexOf(newLesson)
                    if (revertIndex != -1) {
                        revertedLessons[revertIndex] = oldLesson
                        _uiState.value = _uiState.value.copy(
                            lessons = revertedLessons,
                            errorMessage = result.exceptionOrNull()?.message ?: "Failed to update lesson"
                        )
                    }
                }
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun refreshLessons() {
        loadUserLessons()
    }

    fun setGeneratedLessonPlan(lessonPlan: String) {
        _uiState.value = _uiState.value.copy(generatedLessonPlan = lessonPlan)
    }

    fun clearGeneratedLessonPlan() {
        _uiState.value = _uiState.value.copy(generatedLessonPlan = null)
    }

    fun addLessonsFromAI(newLessons: List<ScheduleItem>) {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            if (currentUser != null) {
                // Add lessons to local state immediately
                val currentLessons = _uiState.value.lessons.toMutableList()
                currentLessons.addAll(newLessons)
                _uiState.value = _uiState.value.copy(lessons = currentLessons)

                // Save each lesson to Firebase
                newLessons.forEach { lesson ->
                    val result = scheduleRepository.saveLesson(currentUser.uid, lesson)
                    if (!result.isSuccess) {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = result.exceptionOrNull()?.message ?: "Failed to save lesson: ${lesson.subject}"
                        )
                    }
                }
            }
        }
    }

    fun clearAllLessons() {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            if (currentUser != null) {
                // Get all lessons to delete
                val lessonsToDelete = _uiState.value.lessons.toList()

                // Clear local state immediately for better UX
                _uiState.value = _uiState.value.copy(lessons = emptyList())

                // Delete each lesson from Firebase
                lessonsToDelete.forEach { lesson ->
                    val result = scheduleRepository.deleteLesson(currentUser.uid, lesson.id)
                    if (!result.isSuccess) {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = result.exceptionOrNull()?.message ?: "Failed to delete lesson: ${lesson.subject}"
                        )
                    }
                }
            }
        }
    }
}