package com.emirhankarci.tutorly.data.repository

import com.emirhankarci.tutorly.domain.entity.UserProfile
import com.emirhankarci.tutorly.domain.repository.UserProfileRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserProfileRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserProfileRepository {

    companion object {
        private const val COLLECTION_USER_PROFILES = "user_profiles"
    }

    override suspend fun saveUserProfile(userId: String, profile: UserProfile): Result<Unit> {
        return try {
            val profileData = mapOf(
                "grade" to profile.grade,
                "targetExams" to profile.targetExams,
                "strongSubjects" to profile.strongSubjects,
                "dailyStudyHours" to profile.dailyStudyHours,
                "englishLevel" to profile.englishLevel,
                "ageRange" to profile.ageRange,
                "completed" to true,
                "completedAt" to com.google.firebase.Timestamp.now(),
                "userId" to userId // Add userId for debugging
            )

            firestore.collection(COLLECTION_USER_PROFILES)
                .document(userId)
                .set(profileData)
                .await()

            // Verify the document was saved by reading it back
            val verification = firestore.collection(COLLECTION_USER_PROFILES)
                .document(userId)
                .get()
                .await()

            if (verification.exists() && verification.data?.get("completed") == true) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Profile save verification failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserProfile(userId: String): Result<UserProfile?> {
        return try {
            val document = firestore.collection(COLLECTION_USER_PROFILES)
                .document(userId)
                .get()
                .await()

            if (document.exists()) {
                val data = document.data
                val profile = UserProfile(
                    grade = data?.get("grade") as? String ?: "",
                    targetExams = data?.get("targetExams") as? String ?: "",
                    strongSubjects = data?.get("strongSubjects") as? String ?: "",
                    dailyStudyHours = data?.get("dailyStudyHours") as? String ?: "",
                    englishLevel = data?.get("englishLevel") as? String ?: "",
                    ageRange = data?.get("ageRange") as? String ?: ""
                )
                Result.success(profile)
            } else {
                Result.success(null)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isProfileCompleted(userId: String): Result<Boolean> {
        return try {
            val document = firestore.collection(COLLECTION_USER_PROFILES)
                .document(userId)
                .get()
                .await()

            val isCompleted = document.exists() &&
                             (document.data?.get("completed") as? Boolean ?: false)

            Result.success(isCompleted)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUserProfile(userId: String): Result<Unit> {
        return try {
            firestore.collection(COLLECTION_USER_PROFILES)
                .document(userId)
                .delete()
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun markSubscriptionPurchased(userId: String): Result<Unit> {
        return try {
            // Update the user's profile document with subscription info
            firestore.collection(COLLECTION_USER_PROFILES)
                .document(userId)
                .update(
                    mapOf(
                        "hasCompletedPurchase" to true,
                        "firstPurchaseAt" to com.google.firebase.Timestamp.now()
                    )
                )
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun hasCompletedPurchase(userId: String): Result<Boolean> {
        return try {
            val document = firestore.collection(COLLECTION_USER_PROFILES)
                .document(userId)
                .get()
                .await()

            val hasCompleted = document.exists() &&
                              (document.data?.get("hasCompletedPurchase") as? Boolean ?: false)

            Result.success(hasCompleted)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}