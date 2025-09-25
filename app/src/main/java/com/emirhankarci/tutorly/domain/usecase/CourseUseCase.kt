package com.emirhankarci.tutorly.domain.usecase

import com.emirhankarci.tutorly.data.model.Course
import com.emirhankarci.tutorly.data.repository.FirestoreRepository
import javax.inject.Inject

class CourseUseCase @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) {
    suspend fun createCourse(course: Course): Result<String> {
        return firestoreRepository.createCourse(course)
    }

    suspend fun getCourseById(courseId: String): Result<Course?> {
        return firestoreRepository.getCourseById(courseId)
    }

    suspend fun getCoursesBySubject(subject: String): Result<List<Course>> {
        return firestoreRepository.getCoursesBySubject(subject)
    }

    suspend fun getCoursesByGrade(grade: String): Result<List<Course>> {
        return firestoreRepository.getCoursesByGrade(grade)
    }

    suspend fun getCoursesByTutor(tutorId: String): Result<List<Course>> {
        return firestoreRepository.getCoursesByTutor(tutorId)
    }

    suspend fun updateCourse(courseId: String, course: Course): Result<Unit> {
        return firestoreRepository.updateCourse(courseId, course)
    }

    suspend fun deleteCourse(courseId: String): Result<Unit> {
        return firestoreRepository.deleteCourse(courseId)
    }
}
