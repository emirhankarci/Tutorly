package com.emirhankarci.tutorly.data.repository

import com.emirhankarci.tutorly.data.model.GradeSummary
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SummaryRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private fun collectionForGrade(grade: Int): String = when (grade) {
        9 -> "grade9_summaries"
        10 -> "grade10_summaries"
        else -> "grade10_summaries"
    }

    suspend fun getSummary(grade: Int, subject: String, chapter: String): Result<GradeSummary?> {
        return try {
            val collection = collectionForGrade(grade)
            val snapshot = firestore.collection(collection)
                .whereEqualTo("grade", grade)
                .whereEqualTo("subject", subject)
                .whereEqualTo("chapter", chapter)
                .limit(1)
                .get()
                .await()

            val summary = snapshot.documents.firstOrNull()?.toObject(GradeSummary::class.java)
            Result.success(summary)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


