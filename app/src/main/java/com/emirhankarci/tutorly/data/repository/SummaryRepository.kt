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
        1 -> "grade1_summaries"
        2 -> "grade2_summaries"
        3 -> "grade3_summaries"
        4 -> "grade4_summaries"
        5 -> "grade5_summaries"
        6 -> "grade6_summaries"
        7 -> "grade7_summaries"
        8 -> "grade8_summaries"
        9 -> "grade9_summaries"
        10 -> "grade10_summaries"
        11 -> "grade11_summaries"
        12 -> "grade12_summaries"
        else -> "grade9_summaries"
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


