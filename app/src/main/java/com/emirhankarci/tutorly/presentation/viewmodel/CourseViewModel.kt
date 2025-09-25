package com.emirhankarci.tutorly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhankarci.tutorly.data.model.Course
import com.emirhankarci.tutorly.domain.usecase.CourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseUseCase: CourseUseCase
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun getCoursesBySubject(subject: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            courseUseCase.getCoursesBySubject(subject)
                .onSuccess { courses ->
                    _courses.value = courses
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message
                }
            
            _isLoading.value = false
        }
    }

    fun getCoursesByGrade(grade: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            courseUseCase.getCoursesByGrade(grade)
                .onSuccess { courses ->
                    _courses.value = courses
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message
                }
            
            _isLoading.value = false
        }
    }

    fun createCourse(course: Course) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            courseUseCase.createCourse(course)
                .onSuccess { courseId ->
                    // Başarıyla oluşturuldu, listeyi yenile
                    refreshCourses()
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message
                }
            
            _isLoading.value = false
        }
    }

    fun updateCourse(courseId: String, course: Course) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            courseUseCase.updateCourse(courseId, course)
                .onSuccess {
                    // Başarıyla güncellendi, listeyi yenile
                    refreshCourses()
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message
                }
            
            _isLoading.value = false
        }
    }

    fun deleteCourse(courseId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            courseUseCase.deleteCourse(courseId)
                .onSuccess {
                    // Başarıyla silindi, listeyi yenile
                    refreshCourses()
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message
                }
            
            _isLoading.value = false
        }
    }

    private fun refreshCourses() {
        // Son aranan kriterlere göre listeyi yenile
        // Bu örnekte basit bir yenileme işlemi
        _courses.value = _courses.value
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
