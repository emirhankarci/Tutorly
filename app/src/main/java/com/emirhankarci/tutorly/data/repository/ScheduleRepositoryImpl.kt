package com.emirhankarci.tutorly.data.repository

import com.emirhankarci.tutorly.domain.entity.ScheduleItem
import com.emirhankarci.tutorly.domain.repository.ScheduleRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ScheduleRepository {

    companion object {
        private const val COLLECTION_USER_SCHEDULES = "user_schedules"
        private const val COLLECTION_LESSONS = "lessons"
    }

    override suspend fun saveLesson(userId: String, lesson: ScheduleItem): Result<Unit> {
        return try {
            val lessonData = mapOf(
                "id" to lesson.id,
                "subject" to lesson.subject,
                "topic" to lesson.topic,
                "time" to lesson.time,
                "duration" to lesson.duration,
                "day" to lesson.day,
                "color" to lesson.color.value.toLong(), // Store color as Long
                "notes" to lesson.notes,
                "createdAt" to com.google.firebase.Timestamp.now(),
                "userId" to userId
            )

            firestore.collection(COLLECTION_USER_SCHEDULES)
                .document(userId)
                .collection(COLLECTION_LESSONS)
                .document(lesson.id) // Use lesson ID as document ID
                .set(lessonData)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserLessons(userId: String): Result<List<ScheduleItem>> {
        return try {
            val snapshot = firestore.collection(COLLECTION_USER_SCHEDULES)
                .document(userId)
                .collection(COLLECTION_LESSONS)
                .get()
                .await()

            val lessons = snapshot.documents.mapNotNull { document ->
                try {
                    val data = document.data ?: return@mapNotNull null
                    ScheduleItem(
                        id = data["id"] as? String ?: document.id, // Use stored ID or document ID as fallback
                        subject = data["subject"] as? String ?: "",
                        topic = data["topic"] as? String ?: "",
                        time = data["time"] as? String ?: "",
                        duration = data["duration"] as? String ?: "",
                        day = data["day"] as? String ?: "",
                        color = androidx.compose.ui.graphics.Color((data["color"] as? Long ?: 0xFF2196F3).toULong()),
                        notes = data["notes"] as? String ?: ""
                    )
                } catch (e: Exception) {
                    null // Skip malformed lessons
                }
            }

            Result.success(lessons)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteLesson(userId: String, lessonId: String): Result<Unit> {
        return try {
            firestore.collection(COLLECTION_USER_SCHEDULES)
                .document(userId)
                .collection(COLLECTION_LESSONS)
                .document(lessonId)
                .delete()
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateLesson(userId: String, lessonId: String, lesson: ScheduleItem): Result<Unit> {
        return try {
            val lessonData = mapOf(
                "subject" to lesson.subject,
                "topic" to lesson.topic,
                "time" to lesson.time,
                "duration" to lesson.duration,
                "day" to lesson.day,
                "color" to lesson.color.value.toLong(),
                "notes" to lesson.notes,
                "updatedAt" to com.google.firebase.Timestamp.now()
            )

            firestore.collection(COLLECTION_USER_SCHEDULES)
                .document(userId)
                .collection(COLLECTION_LESSONS)
                .document(lessonId)
                .update(lessonData)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}