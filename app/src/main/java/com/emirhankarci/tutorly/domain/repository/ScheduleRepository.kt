package com.emirhankarci.tutorly.domain.repository

import com.emirhankarci.tutorly.domain.entity.ScheduleItem

interface ScheduleRepository {
    suspend fun saveLesson(userId: String, lesson: ScheduleItem): Result<Unit>
    suspend fun getUserLessons(userId: String): Result<List<ScheduleItem>>
    suspend fun deleteLesson(userId: String, lessonId: String): Result<Unit>
    suspend fun updateLesson(userId: String, lessonId: String, lesson: ScheduleItem): Result<Unit>
}