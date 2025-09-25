package com.emirhankarci.tutorly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.emirhankarci.tutorly.domain.entity.ChapterInfo
import com.emirhankarci.tutorly.domain.entity.GradeSubjectKey
import com.emirhankarci.tutorly.domain.entity.chaptersByGradeAndSubject
import javax.inject.Inject

@HiltViewModel
class ChapterViewModel @Inject constructor() : ViewModel() {

    private val _chapters = MutableStateFlow<List<ChapterInfo>>(emptyList())
    val chapters: StateFlow<List<ChapterInfo>> = _chapters.asStateFlow()

    private val _selectedGrade = MutableStateFlow<Int?>(null)
    val selectedGrade: StateFlow<Int?> = _selectedGrade.asStateFlow()

    private val _selectedSubject = MutableStateFlow<String?>(null)
    val selectedSubject: StateFlow<String?> = _selectedSubject.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun setGrade(grade: Int) {
        _selectedGrade.value = grade
        updateChapters()
    }

    fun setSubject(subject: String) {
        _selectedSubject.value = subject
        updateChapters()
    }

    fun getChaptersByGradeAndSubject(grade: Int, subject: String): List<ChapterInfo> {
        val key = GradeSubjectKey(grade, subject)
        return chaptersByGradeAndSubject[key] ?: emptyList()
    }

    private fun updateChapters() {
        val grade = _selectedGrade.value
        val subject = _selectedSubject.value

        if (grade != null && subject != null) {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val chapterList = getChaptersByGradeAndSubject(grade, subject)
                _chapters.value = chapterList

                if (chapterList.isEmpty()) {
                    _errorMessage.value = "Bu sınıf ve ders için konu bulunamadı"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Konular yüklenirken hata oluştu: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getAllAvailableGrades(): List<Int> {
        return chaptersByGradeAndSubject.keys.map { it.grade }.distinct().sorted()
    }

    fun getAvailableSubjectsForGrade(grade: Int): List<String> {
        return chaptersByGradeAndSubject.keys
            .filter { it.grade == grade }
            .map { it.subject }
            .distinct()
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun reset() {
        _selectedGrade.value = null
        _selectedSubject.value = null
        _chapters.value = emptyList()
        _errorMessage.value = null
    }
}