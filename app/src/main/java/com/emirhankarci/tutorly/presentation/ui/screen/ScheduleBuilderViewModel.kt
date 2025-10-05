package com.emirhankarci.tutorly.presentation.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.data.repository.UserDataRepository
import com.emirhankarci.tutorly.domain.entity.ScheduleData
import com.emirhankarci.tutorly.domain.entity.UserScheduleItem
import com.emirhankarci.tutorly.domain.entity.getSubjectsForGrade
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class ScheduleBuilderUiState(
    val days: List<String> = ScheduleData.weekDays,
    val hours: List<String> = (0..23).map { String.format("%02d:00", it) },
    val subjects: List<String> = emptyList(),
    val currentDayIndex: Int = 0,
    val selections: Map<String, List<UserScheduleItem>> = emptyMap(),
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val saveSuccess: Boolean = false
)

@HiltViewModel
class ScheduleBuilderViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleBuilderUiState())
    val uiState: StateFlow<ScheduleBuilderUiState> = _uiState.asStateFlow()

    init {
        loadUserSubjects()
    }

    private fun loadUserSubjects() {
        viewModelScope.launch {
            try {
                val uid = userDataRepository.getCurrentUserId()
                if (uid != null) {
                    val userProfileResult = userDataRepository.getUserProfile(uid)
                    val userData = userProfileResult.getOrNull()
                    val userGrade = userData?.grade ?: 9
                    val availableSubjects = getSubjectsForGrade(userGrade)
                        .map { it.title }
                    _uiState.value = _uiState.value.copy(subjects = availableSubjects)
                }
            } catch (e: Exception) {
                // Hata durumunda varsayılan listeyı kullan
                _uiState.value = _uiState.value.copy(
                    subjects = listOf(
                        "Matematik",
                        "Fizik",
                        "Kimya",
                        "Biyoloji",
                        "Türk Dili ve Edebiyatı",
                        "Tarih",
                        "Coğrafya"
                    )
                )
            }
        }
    }

    fun addLesson(day: String, time: String, subject: String, notes: String = "") {
        val newItem = UserScheduleItem(
            id = UUID.randomUUID().toString(),
            subject = subject,
            day = day,
            time = time,
            duration = "60 dk",
            notes = notes
        )
        val updated = _uiState.value.selections.toMutableMap()
        val list = updated[day].orEmpty() + newItem
        updated[day] = list
        _uiState.value = _uiState.value.copy(selections = updated)
    }

    fun nextDay() {
        val next = (_uiState.value.currentDayIndex + 1).coerceAtMost(_uiState.value.days.lastIndex)
        _uiState.value = _uiState.value.copy(currentDayIndex = next)
    }

    fun previousDay() {
        val prev = (_uiState.value.currentDayIndex - 1).coerceAtLeast(0)
        _uiState.value = _uiState.value.copy(currentDayIndex = prev)
    }

    fun setCurrentDay(index: Int) {
        val bounded = index.coerceIn(0, _uiState.value.days.lastIndex)
        _uiState.value = _uiState.value.copy(currentDayIndex = bounded)
    }

    fun saveAll(onDone: () -> Unit = {}) {
        val uid = userDataRepository.getCurrentUserId()
        if (uid == null) {
            _uiState.value = _uiState.value.copy(errorMessage = "Kullanıcı oturumu bulunamadı")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true, errorMessage = null)
            try {
                val allItems = _uiState.value.selections.values.flatten()
                userDataRepository.replaceScheduleItems(uid, allItems).getOrThrow()
                _uiState.value = _uiState.value.copy(isSaving = false, saveSuccess = true)
                onDone()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isSaving = false, errorMessage = e.message)
            }
        }
    }
}


